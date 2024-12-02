package com.aoc;

import static java.lang.System.err;
import static java.lang.System.out;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class RedNosedReports {

  private RedNosedReports() {
  }

  public static void main(final String... args) {
    out.printf("Result: %d%n", new RedNosedReports().solveOne("testInput.txt"));
    out.printf("Result: %d%n", new RedNosedReports().solveOne("input.txt"));

    out.printf("Result: %d%n", new RedNosedReports().solveTwo("testInput2.txt"));
    out.printf("Result: %d%n", new RedNosedReports().solveTwo("input2.txt"));
  }

  private static boolean isSafe(int[] levels) {
    boolean increasing = true;
    boolean decreasing = true;

    for (int i = 1; i < levels.length; i++) {
      int diff = Math.abs(levels[i] - levels[i - 1]);
      if (diff < 1 || diff > 3) {
        return false;
      }
      if (levels[i] <= levels[i - 1]) {
        increasing = false;
      }
      if (levels[i] >= levels[i - 1]) {
        decreasing = false;
      }
    }

    return increasing || decreasing;
  }

  private static boolean canBeMadeSafe(int[] levels) {
    for (int i = 0; i < levels.length; i++) {
      int[] modifiedLevels = new int[levels.length - 1];
      for (int j = 0, k = 0; j < levels.length; j++) {
        if (j != i) {
          modifiedLevels[k++] = levels[j];
        }
      }
      if (isSafe(modifiedLevels)) {
        return true;
      }
    }
    return false;
  }

  private long solveOne(final String inputFile) {
    AtomicLong safeReports = new AtomicLong();
    try (Stream<String> input = Files.lines(
        Paths.get(RedNosedReports.class.getClassLoader().getResource(inputFile).toURI()))) {
      input.map(line -> line.split(" "))
          .forEach(number -> {
            var safe = false;
            if (Integer.parseInt(number[0]) > Integer.parseInt(number[1])) {
              for (int i = 1; i < number.length; i++) {
                if (Integer.parseInt(number[i - 1]) > Integer.parseInt(number[i])
                    && Integer.parseInt(number[i - 1]) - Integer.parseInt(number[i]) <= 3) {
                  safe = true;
                } else {
                  safe = false;
                  break;
                }
              }
            } else if (Integer.parseInt(number[0]) < Integer.parseInt(number[1])) {
              for (int i = 1; i < number.length; i++) {
                if (Integer.parseInt(number[i - 1]) < Integer.parseInt(number[i])
                    && Integer.parseInt(number[i]) - Integer.parseInt(number[i - 1]) <= 3) {
                  safe = true;
                } else {
                  safe = false;
                  break;
                }
              }
            } else {
              // do nothing
            }
            if (safe) {
              safeReports.getAndIncrement();
            }
          });
    } catch (URISyntaxException | IOException e) {
      err.println("Error");
      e.printStackTrace();
    }
    return safeReports.get();
  }

  private long solveTwo(String inputFile) {
    AtomicLong safeReports = new AtomicLong();
    try (Stream<String> input = Files.lines(
        Paths.get(RedNosedReports.class.getClassLoader().getResource(inputFile).toURI()))) {
      input.map(line -> line.split(" "))
          .forEach(number -> {
            int[] levels = new int[number.length];
            for (int i = 0; i < number.length; i++) {
              levels[i] = Integer.parseInt(number[i]);
            }
            if (isSafe(levels) || canBeMadeSafe(levels)) {
              safeReports.getAndIncrement();
            }
          });
    } catch (URISyntaxException | IOException e) {
      err.println("Error");
      e.printStackTrace();
    }
    return safeReports.get();
  }

}
