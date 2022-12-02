package com.aoc;

import static java.lang.System.err;
import static java.lang.System.out;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CalorieCounting {

  private CalorieCounting() {
  }

  public static void main(final String... args) {
    out.printf("Result: %d%n", new CalorieCounting().solveOne("testInput.txt"));
    out.printf("Result: %d%n", new CalorieCounting().solveOne("input.txt"));

    out.printf("Result: %d%n", new CalorieCounting().solveTwo("testInput.txt"));
    out.printf("Result: %d%n", new CalorieCounting().solveTwo("input.txt"));
  }

  private long solveTwo(String inputFile) {
    List<Integer> elfsWithMaxCalories = new ArrayList<>();
    try (Stream<String> input = Files.lines(
        Paths.get(CalorieCounting.class.getClassLoader().getResource(inputFile).toURI()))) {
      var calories = 0;
      for (String calorie : input.toList()) {
        if ("".equalsIgnoreCase(calorie)) {
          elfsWithMaxCalories.add(calories);
          elfsWithMaxCalories = elfsWithMaxCalories.stream().sorted(Comparator.reverseOrder()).limit(3).collect(Collectors.toList());
          calories = 0;
        } else {
          calories += Integer.parseInt(calorie);
        }
      }
    } catch (URISyntaxException | IOException e) {
      err.println("Error");
      e.printStackTrace();
    }
    return elfsWithMaxCalories.stream().reduce(Integer::sum).orElse(0);
  }

  private long solveOne(final String inputFile) {
    var maxElfCalories = 0;
    try (Stream<String> input = Files.lines(
        Paths.get(CalorieCounting.class.getClassLoader().getResource(inputFile).toURI()))) {
      var calories = 0;
      for (String calorie : input.toList()) {
        if ("".equalsIgnoreCase(calorie)) {
          if (calories > maxElfCalories) {
            maxElfCalories = calories;
          }
          calories = 0;
        } else {
          calories += Integer.parseInt(calorie);
        }
      }
    } catch (URISyntaxException | IOException e) {
      err.println("Error");
      e.printStackTrace();
    }
    return maxElfCalories;
  }
}
