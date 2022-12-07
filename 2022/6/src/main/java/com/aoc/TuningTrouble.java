package com.aoc;

import static java.lang.System.err;
import static java.lang.System.out;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class TuningTrouble {

  private TuningTrouble() {
  }

  public static void main(final String... args) {
    out.printf("Result: %d%n", new TuningTrouble().solveOne("testInput.txt"));
    out.printf("Result: %d%n", new TuningTrouble().solveOne("input.txt"));

    out.printf("Result: %d%n", new TuningTrouble().solveTwo("testInput.txt"));
    out.printf("Result: %d%n", new TuningTrouble().solveTwo("input.txt"));
  }

  private long solveOne(String inputFile) {
    try (Stream<String> input = Files.lines(
        Paths.get(TuningTrouble.class.getClassLoader().getResource(inputFile).toURI()))) {
      var list = input.toList();
      for (var string : list) {
        for (int a = 0, b = 1, c = 2, d = 3; d < string.length(); a++, b++, c++, d++) {
          if (string.charAt(a) != string.charAt(b)
              && string.charAt(a) != string.charAt(c)
              && string.charAt(a) != string.charAt(d)
              && string.charAt(b) != string.charAt(c)
              && string.charAt(b) != string.charAt(d)
              && string.charAt(c) != string.charAt(d)
          ) {
            return d + 1;
          }
        }
      }
    } catch (URISyntaxException | IOException e) {
      err.println("Error");
      e.printStackTrace();
    }
    return 0;
  }

  private long solveTwo(String inputFile) {
    try (Stream<String> input = Files.lines(
        Paths.get(TuningTrouble.class.getClassLoader().getResource(inputFile).toURI()))) {
      var list = input.toList();
      for (var string : list) {
        for (int ini = 0, end = 14; end < string.length(); ini++, end++) {
          var found = true;
          var test = string.substring(ini, end);
          for (int j = 0; j < test.length(); j++) {
            if (test.substring(j + 1).indexOf(test.charAt(j)) >= 0) {
              found = false;
              break;
            }
          }
          if (found) {
            return end;
          }
        }
      }
    } catch (URISyntaxException | IOException e) {
      err.println("Error");
      e.printStackTrace();
    }
    return 0;
  }

}
