package com.aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.System.err;
import static java.lang.System.out;

public class GiantSquid {
  private GiantSquid() {
  }

  public static void main(String... args) {
    var giantSquid = new GiantSquid();

    out.printf("Result: %d%n", giantSquid.solveOne("testInput.txt"));
    out.printf("Result: %d%n", giantSquid.solveOne("input.txt"));

    out.printf("Result: %d%n", giantSquid.solveTwo("testInput.txt"));
    out.printf("Result: %d%n", giantSquid.solveTwo("input.txt"));
  }

  long solveOne(final String inputFile) {
    try (Stream<String> input = Files.lines(Paths.get(GiantSquid.class.getClassLoader().getResource(inputFile).toURI()))) {
      var inputLines = input.toList();
      var numbers = Arrays.stream(inputLines.get(0).split(",")).mapToInt(Integer::parseInt).toArray();
      var bingoCardList = parseBingoCards(inputLines);
      for (var number : numbers) {
        for (var bingoCard : bingoCardList) {
          if (bingoCard.hasBingoWith(number)) {
            return bingoCard.sumUnmarkedNumbers() * number;
          }
        }
      }
    } catch (URISyntaxException | IOException e) {
      err.println("Error");
      e.printStackTrace();
    }
    return -1;
  }

  long solveTwo(final String inputFile) {
    try (Stream<String> input = Files.lines(Paths.get(GiantSquid.class.getClassLoader().getResource(inputFile).toURI()))) {
      var inputLines = input.toList();
      var numbers = Arrays.stream(inputLines.get(0).split(",")).mapToInt(Integer::parseInt).toArray();
      var bingoCardList = parseBingoCards(inputLines);
      for (var number : numbers) {
        for (var bingoCard : bingoCardList) {
          if (bingoCard.isNotMarkedAsBingo()
              && bingoCard.hasBingoWith(number)) {
            bingoCard.markAsBingo();

            if (bingoCardList.stream().filter(BingoCard::isNotMarkedAsBingo).toList().isEmpty()) {
              return bingoCard.sumUnmarkedNumbers() * number;
            }
          }
        }
      }
    } catch (URISyntaxException | IOException e) {
      err.println("Error");
      e.printStackTrace();
    }
    return -1;
  }

  private List<BingoCard> parseBingoCards(List<String> inputLines) {
    var bingoCardList = new ArrayList<BingoCard>();
    var bingoCard = new BingoCard();
    for (int i = 2; i < inputLines.size(); i++) {
      if (inputLines.get(i).equals("")) {
        bingoCard.parseColumns();
        bingoCardList.add(bingoCard);
        bingoCard = new BingoCard();
      } else {
        bingoCard.addRow(Arrays.stream(inputLines.get(i).split(" ")).filter(s -> !"".equalsIgnoreCase(s)).mapToInt(Integer::parseInt).toArray());
      }
    }
    bingoCard.parseColumns();
    bingoCardList.add(bingoCard);
    return bingoCardList;
  }

  private static class BingoCard {
    private final List<Integer> numbers = new ArrayList<>();
    private final List<List<Integer>> rows = new ArrayList<>();
    private final List<List<Integer>> columns = new ArrayList<>();
    private boolean hasBingo = false;

    void addRow(int[] row) {
      var rowList = new ArrayList<Integer>();
      for (int j : row) {
        rowList.add(j);
      }
      rows.add(rowList);
    }

    void parseColumns() {
      for (var i = 0; i < rows.size(); i++) {
        var column = new ArrayList<Integer>();
        for (var row : rows) {
          column.add(row.get(i));
        }
        columns.add(column);
      }
    }

    public boolean hasBingoWith(int number) {
      boolean bingo = false;
      numbers.add(number);
      for (var row : rows) {
        if (numbers.containsAll(row)) {
          bingo = true;
          break;
        }
      }
      if (!bingo) {
        for (var column : columns) {
          if (numbers.containsAll(column)) {
            bingo = true;
            break;
          }
        }
      }
      return bingo;
    }

    public long sumUnmarkedNumbers() {
      var sum = 0;
      for (var row : rows) {
        sum += row.stream().filter(integer -> !numbers.contains(integer)).reduce(0, Integer::sum);
      }
      return sum;
    }

    public boolean isNotMarkedAsBingo() {
      return !hasBingo;
    }

    public void markAsBingo() {
      hasBingo = true;
    }
  }
}
