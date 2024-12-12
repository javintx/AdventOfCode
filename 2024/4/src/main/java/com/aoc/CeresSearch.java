package com.aoc;

import static java.lang.System.err;
import static java.lang.System.out;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class CeresSearch {

  private CeresSearch() {
  }

  public static void main(final String... args) {
    out.printf("Result: %d%n", new CeresSearch().solveOne("testInput.txt"));
    out.printf("Result: %d%n", new CeresSearch().solveOne("input.txt"));

//    out.printf("Result: %d%n", new CeresSearch().solveTwo("testInput2.txt"));
//    out.printf("Result: %d%n", new CeresSearch().solveTwo("input2.txt"));
  }

  private char[][] loadInput(final String inputFile) {
    try (Stream<String> input = Files.lines(
        Paths.get(CeresSearch.class.getClassLoader().getResource(inputFile).toURI()))) {
      var list = input.toList();
      char[][] output = new char[list.size()][];
      for (int i = 0; i < list.size(); i++) {
        output[i] = list.get(i).toCharArray();
      }
      return output;
    } catch (URISyntaxException | IOException e) {
      err.println("Error");
      e.printStackTrace();
    }
    return new char[0][0];
  }

  private long solveOne(final String inputFile) {
    var input = loadInput(inputFile);
    long result = 0;
    for (int i = 0; i < input.length; i++) {
      for (int j = 0; j < input[i].length; j++) {
        result += findToRight(i, j, input)
            + findToLeft(i, j, input)
            + findToUpper(i, j, input)
            + findToDown(i, j, input)
            + findToUpperRight(i, j, input)
            + findToDownRight(i, j, input)
            + findToUpperLeft(i, j, input)
            + findToDownLeft(i, j, input);
      }
    }
    return result;
  }

  private boolean isXmas(char... chars) {
  return chars.length == 4 && chars[0] == 'X' && chars[1] == 'M' && chars[2] == 'A' && chars[3] == 'S';
}

  private long findToRight(int i, int j, char[][] input) {
    if (j + 4 <= input[i].length) {
      if (isXmas(input[i][j], input[i][j + 1], input[i][j + 2], input[i][j + 3])) {
        return 1;
      }
    }
    return 0;
  }

  private long findToLeft(int i, int j, char[][] input) {
    if (j - 4 >= -1) {
      if (isXmas(input[i][j], input[i][j - 1], input[i][j - 2], input[i][j - 3])) {
        return 1;
      }
    }
    return 0;
  }

  private long findToUpper(int i, int j, char[][] input) {
    if (i - 4 >= -1) {
      if (isXmas(input[i][j], input[i - 1][j], input[i - 2][j], input[i - 3][j])) {
        return 1;
      }
    }
    return 0;
  }

  private long findToDown(int i, int j, char[][] input) {
    if (i + 4 <= input.length) {
      if (isXmas(input[i][j], input[i + 1][j], input[i + 2][j], input[i + 3][j])) {
        return 1;
      }
    }
    return 0;
  }

  private long findToUpperRight(int i, int j, char[][] input) {
    if ((i - 4 >= -1) && (j + 4 <= input[i].length)) {
      if (isXmas(input[i][j], input[i - 1][j + 1], input[i - 2][j + 2], input[i - 3][j + 3])) {
        return 1;
      }
    }
    return 0;
  }

  private long findToDownRight(int i, int j, char[][] input) {
    if ((i + 4 <= input.length) && (j + 4 <= input[i].length)) {
      if (isXmas(input[i][j], input[i + 1][j + 1], input[i + 2][j + 2], input[i + 3][j + 3])) {
        return 1;
      }
    }
    return 0;
  }

  private long findToUpperLeft(int i, int j, char[][] input) {
    if ((i - 4 >= -1) && (j - 4 >= -1)) {
      if (isXmas(input[i][j], input[i - 1][j - 1], input[i - 2][j - 2], input[i - 3][j - 3])) {
        return 1;
      }
    }
    return 0;
  }

  private long findToDownLeft(int i, int j, char[][] input) {
    if ((i + 4 <= input.length) && (j - 4 >= -1)) {
      if (isXmas(input[i][j], input[i + 1][j - 1], input[i + 2][j - 2], input[i + 3][j - 3])) {
        return 1;
      }
    }
    return 0;
  }

  private long solveTwo(String inputFile) {
    AtomicLong multiplyValue = new AtomicLong(0);
    try (Stream<String> input = Files.lines(
        Paths.get(CeresSearch.class.getClassLoader().getResource(inputFile).toURI()))) {
      for (String command : input.toList()) {
        boolean end = false;
        while (!end) {
          var donts = command.indexOf("don't\\(\\)");
          var dos = command.indexOf("do\\(\\)");
          while (dos < donts) {
            Matcher matcher = Pattern.compile("mul\\((\\d+),(\\d+)\\)").matcher(command);
            while (matcher.find()) {
              long num1 = Integer.parseInt(matcher.group(1));
              long num2 = Integer.parseInt(matcher.group(2));
              multiplyValue.updateAndGet(operand -> operand + (num1 * num2));
            }
          }
          end = true;
        }
      }
    } catch (URISyntaxException | IOException e) {
      err.println("Error");
      e.printStackTrace();
    }
    return multiplyValue.get();
  }

}
