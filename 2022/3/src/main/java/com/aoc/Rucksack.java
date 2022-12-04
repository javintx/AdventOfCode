package com.aoc;

import static java.lang.System.err;
import static java.lang.System.out;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Rucksack {

  private static final String PRIORITIES = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

  private Rucksack() {
  }

  public static void main(final String... args) {
    out.printf("Result: %d%n", new Rucksack().solveOne("testInput.txt"));
    out.printf("Result: %d%n", new Rucksack().solveOne("input.txt"));

    out.printf("Result: %d%n", new Rucksack().solveTwo("testInput.txt"));
    out.printf("Result: %d%n", new Rucksack().solveTwo("input.txt"));
  }

  private long solveOne(String inputFile) {
    var score = 0;
    try (Stream<String> input = Files.lines(
        Paths.get(Rucksack.class.getClassLoader().getResource(inputFile).toURI()))) {
      for (var pack : input.toList()) {
        var first = pack.substring(0, pack.length() / 2);
        var second = pack.substring(pack.length() / 2);
        for (var element : first.toCharArray()) {
          if (second.contains(String.valueOf(element))) {
            score += PRIORITIES.indexOf(element) + 1;
            break;
          }
        }
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
        Paths.get(Rucksack.class.getClassLoader().getResource(inputFile).toURI()))) {
      for (var pack : windowed(input.toList(), 3).toList()) {
        for (var element : pack.get(0).toCharArray()) {
          if (pack.get(1).contains(String.valueOf(element)) && pack.get(2).contains(String.valueOf(element))) {
            score += PRIORITIES.indexOf(element) + 1;
            break;
          }
        }
      }
    } catch (URISyntaxException | IOException e) {
      err.println("Error");
      e.printStackTrace();
    }
    return score;
  }

  private <T> Stream<List<T>> windowed(List<T> list, int size) {
    if (size > list.size()) {
      return Stream.empty();
    }
    return IntStream
        .range(0, list.size() / size)
        .mapToObj(start -> list.subList(start * size, (start * size) + size));
  }
}
