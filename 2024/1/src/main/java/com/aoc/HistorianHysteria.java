package com.aoc;

import static java.lang.System.err;
import static java.lang.System.out;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class HistorianHysteria {

  private HistorianHysteria() {
  }

  public static void main(final String... args) {
    out.printf("Result: %d%n", new HistorianHysteria().solveOne("testInput.txt"));
    out.printf("Result: %d%n", new HistorianHysteria().solveOne("input.txt"));

    out.printf("Result: %d%n", new HistorianHysteria().solveTwo("testInput2.txt"));
    out.printf("Result: %d%n", new HistorianHysteria().solveTwo("input2.txt"));
  }

  private long solveOne(final String inputFile) {
    long distance = 0;
    try (Stream<String> input = Files.lines(
        Paths.get(HistorianHysteria.class.getClassLoader().getResource(inputFile).toURI()))) {
      var firstLocations = new ArrayList<Integer>();
      var secondLocations = new ArrayList<Integer>();
      for (String locationValue : input.toList()) {
        firstLocations.add(Integer.parseInt(locationValue.split(" {3}")[0]));
        secondLocations.add(Integer.parseInt(locationValue.split(" {3}")[1]));
      }
      firstLocations.sort(Integer::compareTo);
      secondLocations.sort(Integer::compareTo);
      for (int i = 0; i < firstLocations.size(); i++) {
        distance += Math.abs(firstLocations.get(i) - secondLocations.get(i));
      }
    } catch (URISyntaxException | IOException e) {
      err.println("Error");
      e.printStackTrace();
    }
    return distance;
  }

  private long solveTwo(String inputFile) {
    long score = 0;
    try (Stream<String> input = Files.lines(
        Paths.get(HistorianHysteria.class.getClassLoader().getResource(inputFile).toURI()))) {
      var firstLocations = new ArrayList<Integer>();
      var secondLocations = new ArrayList<Integer>();
      for (String locationValue : input.toList()) {
        firstLocations.add(Integer.parseInt(locationValue.split(" {3}")[0]));
        secondLocations.add(Integer.parseInt(locationValue.split(" {3}")[1]));
      }
      for (int location : firstLocations) {
        score += location * secondLocations.stream().filter(i -> i == location).count();
      }
    } catch (URISyntaxException | IOException e) {
      err.println("Error");
      e.printStackTrace();
    }
    return score;
  }

}
