package com.aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.err;
import static java.lang.System.out;

public class BinaryDiagnostic {
  private BinaryDiagnostic() {
  }

  public static void main(String... args) {
    var binaryDiagnostic = new BinaryDiagnostic();

    binaryDiagnostic.solveOne("testInput.txt");
    binaryDiagnostic.solveOne("input.txt");

    binaryDiagnostic.solveTwo("testInput.txt");
    binaryDiagnostic.solveTwo("input.txt");
  }

  void solveOne(final String commandsFile) {
    try (Stream<String> binaryNumbers = Files.lines(Paths.get(BinaryDiagnostic.class.getClassLoader().getResource(commandsFile).toURI()))) {

      var binaryNumberList = binaryNumbers.toList();
      var pairs = new ArrayList<Pair>(binaryNumberList.size());
      for (var index = 0; index < binaryNumberList.get(0).length(); index++) {
        pairs.add(new Pair());
      }

      for (var binaryNumber : binaryNumberList) {
        var number = binaryNumber.toCharArray();
        for (var index = 0; index < number.length; index++) {
          pairs.get(index).incrementBy(number[index]);
        }
      }

      long gamma = Long.parseLong(pairs.stream().map(Pair::mostCommon).collect(Collectors.joining()), 2);
      long epsilon = Long.parseLong(pairs.stream().map(Pair::leastCommon).collect(Collectors.joining()), 2);
      out.printf("Result: %d%n", gamma * epsilon);
    } catch (URISyntaxException | IOException e) {
      err.println("Error");
      e.printStackTrace();
    }
  }

  void solveTwo(final String commandsFile) {
    try (Stream<String> binaryNumbers = Files.lines(Paths.get(BinaryDiagnostic.class.getClassLoader().getResource(commandsFile).toURI()))) {

      var binaryNumberList = binaryNumbers.toList();
      long oxygenGeneratorRating = oxygenGeneratorRating(binaryNumberList);
      long co2ScrubberRating = co2ScrubberRating(binaryNumberList);

      out.printf("Result: %d%n", oxygenGeneratorRating * co2ScrubberRating);
    } catch (URISyntaxException | IOException e) {
      err.println("Error");
      e.printStackTrace();
    }
  }

  private long oxygenGeneratorRating(final List<String> binaryNumberList) {
    var index = new AtomicInteger();
    var filter = new StringBuilder();
    var filteredNumberList = binaryNumberList;
    do {
      var ratingValue = filteredNumberList.stream().map(s -> s.toCharArray()[index.get()]).filter(character -> character == '1').count();
      index.incrementAndGet();
      if (ratingValue * 2 >= filteredNumberList.size()) {
        filter.append('1');
      } else {
        filter.append('0');
      }
      filteredNumberList = filteredNumberList.stream().filter(s -> s.startsWith(filter.toString())).toList();
    } while (filteredNumberList.size() > 1);

    return Long.parseLong(filteredNumberList.get(0), 2);
  }

  private long co2ScrubberRating(final List<String> binaryNumberList) {
    var index = new AtomicInteger();
    var filter = new StringBuilder();
    var filteredNumberList = binaryNumberList;
    do {
      var ratingValue = filteredNumberList.stream().map(s -> s.toCharArray()[index.get()]).filter(character -> character == '0').count();
      index.incrementAndGet();
      if (ratingValue * 2 <= filteredNumberList.size()) {
        filter.append('0');
      } else {
        filter.append('1');
      }
      filteredNumberList = filteredNumberList.stream().filter(s -> s.startsWith(filter.toString())).toList();
    } while (filteredNumberList.size() > 1);

    return Long.parseLong(filteredNumberList.get(0), 2);
  }

  private static class Pair {
    private int one;
    private int zero;

    Pair() {
      one = 0;
      zero = 0;
    }

    void incrementBy(char value) {
      if (value == '1') {
        incrementOnes();
      } else {
        incrementZeros();
      }
    }

    private void incrementOnes() {
      one++;
    }

    private void incrementZeros() {
      zero++;
    }

    String mostCommon() {
      return one > zero ? "1" : "0";
    }

    String leastCommon() {
      return one < zero ? "1" : "0";
    }
  }

}
