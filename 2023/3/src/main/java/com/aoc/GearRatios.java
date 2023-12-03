package com.aoc;

import static java.lang.System.err;
import static java.lang.System.out;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GearRatios {

  private GearRatios() {
  }

  public static void main(final String... args) {
//    out.printf("Result: %d%n", new GearRatios().solveOne("testInput.txt"));
//    out.printf("Result: %d%n", new GearRatios().solveOne("input.txt"));

    out.printf("Result: %d%n", new GearRatios().solveTwo("testInput.txt"));
//    out.printf("Result: %d%n", new GearRatios().solveTwo("input.txt"));
  }


  private long solveTwo(final String inputFile) {
    var sumGearNumbers = 0;
    var engineSchema = loadEngineSchema(inputFile);
//    for (var line = 0; line < engineSchema.length; line++) {
//      var schemaLine = engineSchema[line];
//      for (int lineIndex = 0; lineIndex < schemaLine.length(); lineIndex++) {
//        if (isAGear(schemaLine.charAt(lineIndex))) {
//
//        }
//      }
//    }
    return sumGearNumbers;
  }


  private int isAValidNumber(String[] engineSchema, int line, int lineIndex) {
    try {
      var character = engineSchema[line].charAt(lineIndex);
      return character >= 48 && character <= 57 ? 1 : 0;
    } catch (IndexOutOfBoundsException e) {
      return 0;
    }
  }

  private boolean isAGear(char c) {
    return c == 42;
  }

  private long solveOne(final String inputFile) {
    var sumPartNumbers = 0;
    var engineSchema = loadEngineSchema(inputFile);
    for (var line = 0; line < engineSchema.length; line++) {
      var schemaLine = engineSchema[line];
      var wasANumber = false;
      var isAdjacentToAValidSymbol = false;
      var num = "";
      for (int lineIndex = 0; lineIndex < schemaLine.length(); lineIndex++) {
        if (isANumber(schemaLine.charAt(lineIndex))) {
          wasANumber = true;
          num = num.concat(String.valueOf(schemaLine.charAt(lineIndex)));
          if (!isAdjacentToAValidSymbol) {
            // find adjacent valid symbol
            isAdjacentToAValidSymbol = findAdjacentValidSymbol(engineSchema, line, lineIndex);
          }
        } else {
          if (wasANumber && isAdjacentToAValidSymbol) {
            // sum
            sumPartNumbers += Integer.parseInt(num);
          }
          wasANumber = false;
          isAdjacentToAValidSymbol = false;
          num = "";
        }
        if (wasANumber && isAdjacentToAValidSymbol && lineIndex == schemaLine.length() - 1) {
          // sum nunmber at the end of the line
          sumPartNumbers += Integer.parseInt(num);
          wasANumber = false;
          isAdjacentToAValidSymbol = false;
          num = "";
        }
      }
    }
    return sumPartNumbers;
  }

  private boolean findAdjacentValidSymbol(String[] engineSchema, int line, int lineIndex) {
    return isAValidSymbol(engineSchema, line - 1, lineIndex - 1)
        || isAValidSymbol(engineSchema, line - 1, lineIndex)
        || isAValidSymbol(engineSchema, line - 1, lineIndex + 1)
        || isAValidSymbol(engineSchema, line, lineIndex - 1)
        || isAValidSymbol(engineSchema, line, lineIndex + 1)
        || isAValidSymbol(engineSchema, line + 1, lineIndex - 1)
        || isAValidSymbol(engineSchema, line + 1, lineIndex)
        || isAValidSymbol(engineSchema, line + 1, lineIndex + 1);
  }

  private boolean isAValidSymbol(String[] engineSchema, int line, int lineIndex) {
    try {
      var character = engineSchema[line].charAt(lineIndex);
      return (character < 48 && character != 46) || character > 57;
    } catch (IndexOutOfBoundsException e) {
      return false;
    }
  }

  private boolean isANumber(char character) {
    return character >= 48 && character <= 57;
  }

  private String[] loadEngineSchema(String inputFile) {
    try {
      return Files.lines(
              Paths.get(
                  GearRatios.class.getClassLoader().getResource(inputFile).toURI()))
          .toArray(String[]::new);
    } catch (URISyntaxException | IOException e) {
      err.println("Error");
      e.printStackTrace();
    }
    return new String[0];
  }

}
