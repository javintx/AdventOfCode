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

public class RockPaperScissors {

  private RockPaperScissors() {
  }

  public static void main(final String... args) {
    out.printf("Result: %d%n", new RockPaperScissors().solveOne("testInput.txt"));
    out.printf("Result: %d%n", new RockPaperScissors().solveOne("input.txt"));

    out.printf("Result: %d%n", new RockPaperScissors().solveTwo("testInput.txt"));
    out.printf("Result: %d%n", new RockPaperScissors().solveTwo("input.txt"));
  }

  private long solveOne(String inputFile) {
    var score = 0;
    try (Stream<String> input = Files.lines(
        Paths.get(RockPaperScissors.class.getClassLoader().getResource(inputFile).toURI()))) {
      for (String round : input.toList()) {
        String elf = round.split(" ")[0];
        String you = round.split(" ")[1];
        score += switch (elf) {
          case "A" -> switch (you) {
            case "X" -> 1 + 3;
            case "Y" -> 2 + 6;
            case "Z" -> 3 + 0;
            default -> 0;
          };
          case "B" -> switch (you) {
            case "X" -> 1 + 0;
            case "Y" -> 2 + 3;
            case "Z" -> 3 + 6;
            default -> 0;
          };
          case "C" -> switch (you) {
            case "X" -> 1 + 6;
            case "Y" -> 2 + 0;
            case "Z" -> 3 + 3;
            default -> 0;
          };
          default -> 0;
        };
      }
    } catch (URISyntaxException | IOException e) {
      err.println("Error");
      e.printStackTrace();
    }
    return score;
  }

  private long solveTwo(String inputFile) {
    var score = 0;
    try (Stream<String> input = Files.lines(
        Paths.get(RockPaperScissors.class.getClassLoader().getResource(inputFile).toURI()))) {
      for (String round : input.toList()) {
        String elf = round.split(" ")[0];
        String you = round.split(" ")[1];
        score += switch (elf) {
          case "A" -> switch (you) {
            case "X" -> 3 + 0;
            case "Y" -> 1 + 3;//
            case "Z" -> 2 + 6;
            default -> 0;
          };
          case "B" -> switch (you) {
            case "X" -> 1 + 0;//
            case "Y" -> 2 + 3;
            case "Z" -> 3 + 6;
            default -> 0;
          };
          case "C" -> switch (you) {
            case "X" -> 2 + 0;
            case "Y" -> 3 + 3;
            case "Z" -> 1 + 6;//
            default -> 0;
          };
          default -> 0;
        };
      }
    } catch (URISyntaxException | IOException e) {
      err.println("Error");
      e.printStackTrace();
    }
    return score;
  }
}
