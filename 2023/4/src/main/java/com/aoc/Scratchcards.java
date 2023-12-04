package com.aoc;

import static java.lang.System.err;
import static java.lang.System.out;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Scratchcards {

  private Scratchcards() {
  }

  public static void main(final String... args) {
    out.printf("Result: %d%n", new Scratchcards().solveOne("testInput.txt"));
    out.printf("Result: %d%n", new Scratchcards().solveOne("input.txt"));

//    out.printf("Result: %d%n", new Scratchcards().solveTwo("testInput.txt"));
//    out.printf("Result: %d%n", new Scratchcards().solveTwo("input.txt"));
  }


  private long solveTwo(final String inputFile) {
    var result = 0;
    var input = loadInput(inputFile);
    for(var card : input) {
      var wins = card.cardNumbers.stream().filter(card.winningNumbers::contains).count();

    }
    return result;
  }

  private long solveOne(final String inputFile) {
    var result = 0;
    var input = loadInput(inputFile);
    for(var card : input) {
      var wins = card.cardNumbers.stream().filter(card.winningNumbers::contains).count();
      result += doubleFor(wins);
    }
    return result;
  }

  private int doubleFor(long wins) {
    if(wins == 0) {
      return 0;
    }
    AtomicInteger doubleValue = new AtomicInteger(1);
    IntStream.range(1, (int) wins).forEach(value -> doubleValue.updateAndGet(v -> v * 2));
    return doubleValue.get();
  }

  private List<Card> loadInput(String inputFile) {
    try (Stream<String> input = Files.lines(
        Paths.get(Scratchcards.class.getClassLoader().getResource(inputFile).toURI()))) {
      return input.toList().stream().map(card ->
        new Card(
            Arrays.stream(
                card.split(":")[1].trim().split("\\|")[0].trim().replace("  ", " ").split(" "))
                .map(number -> Integer.parseInt(number.trim()))
                .toList(),
            Arrays.stream(
                card.split(":")[1].trim().split("\\|")[1].trim().replace("  ", " ").split(" "))
                .map(number -> Integer.parseInt(number.trim()))
                .toList()
        )
      ).toList();
    } catch (URISyntaxException | IOException e) {
      err.println("Error");
      e.printStackTrace();
    }
    return new ArrayList<>(0);
  }

  private static final class Card {
    final List<Integer> winningNumbers;
    final List<Integer> cardNumbers;
    int points = 0;

    public Card(List<Integer> winningNumbers, List<Integer> cardNumbers) {
      this.winningNumbers = winningNumbers;
      this.cardNumbers = cardNumbers;
    }
  }

}
