package com.aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.lang.System.err;
import static java.lang.System.out;

public class HydrothermalVenture {
  private HydrothermalVenture() {
  }

  public static void main(String... args) {
    var hydrothermalVenture = new HydrothermalVenture();

    out.printf("Result: %d%n", hydrothermalVenture.solveOne("testInput.txt", 10));
    out.printf("Result: %d%n", hydrothermalVenture.solveOne("input.txt", 1000));

    out.printf("Result: %d%n", hydrothermalVenture.solveTwo("testInput.txt", 10));
    out.printf("Result: %d%n", hydrothermalVenture.solveTwo("input.txt", 1000));
  }

  private static boolean filterValidValues(final String value) {
    var pattern = Pattern.compile("([0-9]+),([0-9]+) -> ([0-9]+),([0-9]+)");
    var matcher = pattern.matcher(value);
    if (matcher.find()) {
      var x1 = matcher.group(1);
      var y1 = matcher.group(2);
      var x2 = matcher.group(3);
      var y2 = matcher.group(4);

      return x1.equals(x2) || y1.equals(y2);
    }
    return false;
  }

  long solveOne(final String inputFile, final int size) {
    try (Stream<String> input = Files.lines(Paths.get(HydrothermalVenture.class.getClassLoader().getResource(inputFile).toURI()))) {
      var inputLines = input.filter(HydrothermalVenture::filterValidValues).map(Coordinate::parse).toList();
      var diagram = new Diagram(size);
      inputLines.forEach(diagram::addCoordinate);
      return diagram.getDangerousAreas();
    } catch (URISyntaxException | IOException e) {
      err.println("Error");
      e.printStackTrace();
    }
    return -1;
  }

  long solveTwo(final String inputFile, final int size) {
    try (Stream<String> input = Files.lines(Paths.get(HydrothermalVenture.class.getClassLoader().getResource(inputFile).toURI()))) {
      var inputLines = input.map(Coordinate::parse).toList();
      var diagram = new ExtendedDiagram(size);
      inputLines.forEach(diagram::addCoordinate);
      return diagram.getDangerousAreas();
    } catch (URISyntaxException | IOException e) {
      err.println("Error");
      e.printStackTrace();
    }
    return -1;
  }

  private record Coordinate(int x1, int y1, int x2, int y2) {

    static Coordinate parse(final String line) {
      var pattern = Pattern.compile("([0-9]+),([0-9]+) -> ([0-9]+),([0-9]+)");
      var matcher = pattern.matcher(line);
      if (matcher.find()) {
        var x1 = Integer.parseInt(matcher.group(1));
        var y1 = Integer.parseInt(matcher.group(2));
        var x2 = Integer.parseInt(matcher.group(3));
        var y2 = Integer.parseInt(matcher.group(4));

        return new Coordinate(x1, y1, x2, y2);
      }
      throw new UnknownError();
    }

    boolean isColumn() {
      return x1 == x2;
    }

    boolean isRow() {
      return y1 == y2;
    }

    public int getMinColumn() {
      return Math.min(y1, y2);
    }

    public int getMaxColumn() {
      return Math.max(y1, y2);
    }

    public int getMinRow() {
      return Math.min(x1, x2);
    }

    public int getMaxRow() {
      return Math.max(x1, x2);
    }

    public boolean isBackSlash() {
      return (getMinRow() == x1 && getMinColumn() == y1) || (getMinRow() == x2 && getMinColumn() == y2);
    }
  }

  private static class Diagram {
    protected final int[][] map;
    private final int size;

    protected Diagram(int size) {
      map = new int[size][size];
      this.size = size;
    }

    void addCoordinate(final Coordinate coordinate) {
      if (coordinate.isRow()) {
        addRow(coordinate);
      } else {
        addColumn(coordinate);
      }
    }

    protected void addColumn(Coordinate coordinate) {
      for (var row = coordinate.getMinColumn(); row <= coordinate.getMaxColumn(); row++) {
        map[row][coordinate.x1] = ++map[row][coordinate.x1];
      }
    }

    protected void addRow(Coordinate coordinate) {
      for (int column = coordinate.getMinRow(); column <= coordinate.getMaxRow(); column++) {
        map[coordinate.y1][column] = ++map[coordinate.y1][column];
      }
    }

    long getDangerousAreas() {
      long dangerousAreas = 0;
      for (var i = 0; i < size; i++) {
        dangerousAreas += Arrays.stream(map[i]).filter(value -> value > 1).count();
      }
      return dangerousAreas;
    }
  }

  private static class ExtendedDiagram extends Diagram {
    protected ExtendedDiagram(int size) {
      super(size);
    }

    @Override
    void addCoordinate(final Coordinate coordinate) {
      if (coordinate.isRow()) {
        addRow(coordinate);
      } else if (coordinate.isColumn()) {
        addColumn(coordinate);
      } else {
        addDiagonal(coordinate);
      }
    }

    protected void addDiagonal(Coordinate coordinate) {
      if (coordinate.isBackSlash()) {
        for (int row = coordinate.getMinColumn(), column = coordinate.getMinRow(); row <= coordinate.getMaxColumn() && column <= coordinate.getMaxRow(); row++, column++) {
          map[row][column] = ++map[row][column];
        }
      } else {
        for (int row = coordinate.getMinColumn(), column = coordinate.getMaxRow(); row <= coordinate.getMaxColumn() && column >= coordinate.getMinRow(); row++, column--) {
          map[row][column] = ++map[row][column];
        }
      }
    }
  }
}
