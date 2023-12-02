package com.aoc;

import static java.lang.System.err;
import static java.lang.System.out;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class CubeConundrum {

  private CubeConundrum() {
  }

  public static void main(final String... args) {
    out.printf("Result: %d%n", new CubeConundrum().solveOne("testInput.txt"));
    out.printf("Result: %d%n", new CubeConundrum().solveOne("input.txt"));

    out.printf("Result: %d%n", new CubeConundrum().solveTwo("testInput.txt"));
    out.printf("Result: %d%n", new CubeConundrum().solveTwo("input.txt"));
  }

  private long solveOne(final String inputFile) {
    var gamesSum = 0;
    try (Stream<String> input = Files.lines(
        Paths.get(CubeConundrum.class.getClassLoader().getResource(inputFile).toURI()))) {
      for (String game : input.toList()) {
        gamesSum += Arrays.stream(game
                .split("Game ([0-9])*:")[1]
                .split(";"))
            .noneMatch(set ->
                Arrays.stream(set
                        .split(","))
                    .anyMatch(this::outOfBound))
            ? Integer.parseInt(game.split(":(.*)")[0].split(" ")[1])
            : 0;
      }
    } catch (URISyntaxException | IOException e) {
      err.println("Error");
      e.printStackTrace();
    }
    return gamesSum;
  }

  private boolean outOfBound(String s) {
    var number = Integer.parseInt(s.trim().split(" ")[0]);
    var color = s.trim().split(" ")[1];

    // 12 red cubes, 13 green cubes, and 14 blue cubes
    if ("red".equals(color) && number > 12) {
      return true;
    }
    if ("green".equals(color) && number > 13) {
      return true;
    }
    return "blue".equals(color) && number > 14;
  }

  private long solveTwo(String inputFile) {
    var gamesSum = 0;
    try (Stream<String> input = Files.lines(
        Paths.get(CubeConundrum.class.getClassLoader().getResource(inputFile).toURI()))) {
      for (String game : input.toList()) {
        var gameSet = new GameSet();
        Arrays.stream(game
                .split("Game ([0-9])*:")[1]
                .split(";"))
            .forEach(set ->
                Arrays.stream(set
                        .split(","))
                    .forEach(gs -> parseGame(gameSet, gs)));
        gamesSum += gameSet.powah();
      }
    } catch (URISyntaxException | IOException e) {
      err.println("Error");
      e.printStackTrace();
    }
    return gamesSum;
  }

  private void parseGame(GameSet gameSet, String gs) {
    var number = Integer.parseInt(gs.trim().split(" ")[0]);
    var color = gs.trim().split(" ")[1];

    if ("red".equals(color)) {
      gameSet.addMaximumRed(number);
    }
    if ("green".equals(color)) {
      gameSet.addMaximumGreen(number);
    }
    if ("blue".equals(color)) {
      gameSet.addMaximumBlue(number);
    }
  }

  private static class GameSet {

    int red = 0;
    int blue = 0;
    int green = 0;

    void addMaximumRed(int newRed) {
      if (newRed > red) {
        red = newRed;
      }
    }

    void addMaximumBlue(int newBlue) {
      if (newBlue > blue) {
        blue = newBlue;
      }
    }

    void addMaximumGreen(int newGreen) {
      if (newGreen > green) {
        green = newGreen;
      }
    }

    int powah() {
      return red * blue * green;
    }
  }

}
