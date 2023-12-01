package com.aoc;

import static java.lang.System.err;
import static java.lang.System.out;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Trebuchet {

  private Trebuchet() {
  }

  public static void main(final String... args) {
    out.printf("Result: %d%n", new Trebuchet().solveOne("testInput.txt"));
    out.printf("Result: %d%n", new Trebuchet().solveOne("input.txt"));

    out.printf("Result: %d%n", new Trebuchet().solveTwo("testInput2.txt"));
    out.printf("Result: %d%n", new Trebuchet().solveTwo("input2.txt"));
  }

  private long solveOne(final String inputFile) {
    var calibration = 0;
    try (Stream<String> input = Files.lines(
        Paths.get(Trebuchet.class.getClassLoader().getResource(inputFile).toURI()))) {
      for (String calibrationValue : input.toList()) {
        var calibrationNumbers = calibrationValue.chars().filter(value -> value >= 48 && value <= 57).toArray();
        calibration += Integer.parseInt(""
            + (char) calibrationNumbers[0]
            + (char) calibrationNumbers[calibrationNumbers.length - 1]);
      }
    } catch (URISyntaxException | IOException e) {
      err.println("Error");
      e.printStackTrace();
    }
    return calibration;
  }

  private long solveTwo(String inputFile) {
    var calibration = 0;
    try (Stream<String> input = Files.lines(
        Paths.get(Trebuchet.class.getClassLoader().getResource(inputFile).toURI()))) {
      for (String calibrationValue : input.toList()) {
        calibration += Integer.parseInt(""
            + findFirstNumber(calibrationValue)
            + findSecondNumber(calibrationValue));
      }
    } catch (URISyntaxException | IOException e) {
      err.println("Error");
      e.printStackTrace();
    }
    return calibration;
  }

  private char findSecondNumber(String calibrationValue) {
    var integerArray = calibrationValue.chars().filter(value -> value >= 49 && value <= 57).toArray();
    var indexOfInteger =
        integerArray.length > 0 ? calibrationValue.lastIndexOf((char) integerArray[integerArray.length - 1])
            : Integer.MIN_VALUE;

    Integer number = findSecondNumberValue(calibrationValue, indexOfInteger);

    if (number == null) {
      return (char) integerArray[integerArray.length - 1];
    } else {
      return Character.forDigit(number, 10);
    }
  }

  private Integer findSecondNumberValue(String calibrationValue, int indexOfInteger) {
    var lastIndex = Integer.MIN_VALUE;
    var numberValue = -1;
    if (calibrationValue.contains("one") && calibrationValue.lastIndexOf("one") > lastIndex) {
      lastIndex = calibrationValue.lastIndexOf("one");
      numberValue = 1;
    }
    if (calibrationValue.contains("two") && calibrationValue.lastIndexOf("two") > lastIndex) {
      lastIndex = calibrationValue.lastIndexOf("two");
      numberValue = 2;
    }
    if (calibrationValue.contains("three") && calibrationValue.lastIndexOf("three") > lastIndex) {
      lastIndex = calibrationValue.lastIndexOf("three");
      numberValue = 3;
    }
    if (calibrationValue.contains("four") && calibrationValue.lastIndexOf("four") > lastIndex) {
      lastIndex = calibrationValue.lastIndexOf("four");
      numberValue = 4;
    }
    if (calibrationValue.contains("five") && calibrationValue.lastIndexOf("five") > lastIndex) {
      lastIndex = calibrationValue.lastIndexOf("five");
      numberValue = 5;
    }
    if (calibrationValue.contains("six") && calibrationValue.lastIndexOf("six") > lastIndex) {
      lastIndex = calibrationValue.lastIndexOf("six");
      numberValue = 6;
    }
    if (calibrationValue.contains("seven") && calibrationValue.lastIndexOf("seven") > lastIndex) {
      lastIndex = calibrationValue.lastIndexOf("seven");
      numberValue = 7;
    }
    if (calibrationValue.contains("eight") && calibrationValue.lastIndexOf("eight") > lastIndex) {
      lastIndex = calibrationValue.lastIndexOf("eight");
      numberValue = 8;
    }
    if (calibrationValue.contains("nine") && calibrationValue.lastIndexOf("nine") > lastIndex) {
      lastIndex = calibrationValue.lastIndexOf("nine");
      numberValue = 9;
    }
    if (indexOfInteger > lastIndex) {
      lastIndex = -1;
    }
    return lastIndex == -1 ? null : numberValue;
  }

  private char findFirstNumber(String calibrationValue) {
    var integerArray = calibrationValue.chars().filter(value -> value >= 48 && value <= 57).toArray();
    var indexOfInteger = integerArray.length > 0 ? calibrationValue.indexOf((char) integerArray[0]) : Integer.MAX_VALUE;

    Integer number = findFirstNumberValue(calibrationValue, indexOfInteger);

    if (number == null) {
      return (char) integerArray[0];
    } else {
      return Character.forDigit(number, 10);
    }
  }

  private Integer findFirstNumberValue(String calibrationValue, int indexOfInteger) {
    var firstIndex = Integer.MAX_VALUE;
    var numberValue = -1;
    if (calibrationValue.contains("one") && calibrationValue.indexOf("one") < firstIndex) {
      firstIndex = calibrationValue.indexOf("one");
      numberValue = 1;
    }
    if (calibrationValue.contains("two") && calibrationValue.indexOf("two") < firstIndex) {
      firstIndex = calibrationValue.indexOf("two");
      numberValue = 2;
    }
    if (calibrationValue.contains("three") && calibrationValue.indexOf("three") < firstIndex) {
      firstIndex = calibrationValue.indexOf("three");
      numberValue = 3;
    }
    if (calibrationValue.contains("four") && calibrationValue.indexOf("four") < firstIndex) {
      firstIndex = calibrationValue.indexOf("four");
      numberValue = 4;
    }
    if (calibrationValue.contains("five") && calibrationValue.indexOf("five") < firstIndex) {
      firstIndex = calibrationValue.indexOf("five");
      numberValue = 5;
    }
    if (calibrationValue.contains("six") && calibrationValue.indexOf("six") < firstIndex) {
      firstIndex = calibrationValue.indexOf("six");
      numberValue = 6;
    }
    if (calibrationValue.contains("seven") && calibrationValue.indexOf("seven") < firstIndex) {
      firstIndex = calibrationValue.indexOf("seven");
      numberValue = 7;
    }
    if (calibrationValue.contains("eight") && calibrationValue.indexOf("eight") < firstIndex) {
      firstIndex = calibrationValue.indexOf("eight");
      numberValue = 8;
    }
    if (calibrationValue.contains("nine") && calibrationValue.indexOf("nine") < firstIndex) {
      firstIndex = calibrationValue.indexOf("nine");
      numberValue = 9;
    }
    if (indexOfInteger < firstIndex) {
      firstIndex = -1;
    }
    return firstIndex == -1 ? null : numberValue;
  }

}
