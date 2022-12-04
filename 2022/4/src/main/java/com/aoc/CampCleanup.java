package com.aoc;

import static java.lang.System.err;
import static java.lang.System.out;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class CampCleanup {

  private CampCleanup() {
  }

  public static void main(final String... args) {
    out.printf("Result: %d%n", new CampCleanup().solveOne("testInput.txt"));
    out.printf("Result: %d%n", new CampCleanup().solveOne("input.txt"));

    out.printf("Result: %d%n", new CampCleanup().solveTwo("testInput.txt"));
    out.printf("Result: %d%n", new CampCleanup().solveTwo("input.txt"));
  }

  private long solveOne(String inputFile) {
    var score = 0;
    try (Stream<String> input = Files.lines(
        Paths.get(CampCleanup.class.getClassLoader().getResource(inputFile).toURI()))) {
      for (String ranges : input.toList()) {
        var first = ranges.split(",")[0];
        var second = ranges.split(",")[1];
        if (
            (Integer.parseInt(first.split("-")[0]) >= Integer.parseInt(second.split("-")[0])
                && Integer.parseInt(first.split("-")[1]) <= Integer.parseInt(second.split("-")[1]))
                || (Integer.parseInt(first.split("-")[0]) <= Integer.parseInt(second.split("-")[0])
                && Integer.parseInt(first.split("-")[1]) >= Integer.parseInt(second.split("-")[1]))
        ) {
          score++;
        }
      }
    } catch (URISyntaxException |
             IOException e) {
      err.println("Error");
      e.printStackTrace();
    }
    return score;
  }

  private long solveTwo(String inputFile) {
    var score = 0;
    try (Stream<String> input = Files.lines(
        Paths.get(CampCleanup.class.getClassLoader().getResource(inputFile).toURI()))) {
      for (String ranges : input.toList()) {
        var first = ranges.split(",")[0];
        var second = ranges.split(",")[1];
        if (!
            (Integer.parseInt(first.split("-")[1]) < Integer.parseInt(second.split("-")[0])
                || Integer.parseInt(first.split("-")[0]) > Integer.parseInt(second.split("-")[1]))
        ) {
          score++;
        }
      }
    } catch (URISyntaxException |
             IOException e) {
      err.println("Error");
      e.printStackTrace();
    }
    return score;
  }
}
