package com.aoc;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.lang.System.err;
import static java.lang.System.out;

public class Dive {
  private Dive() {
  }

  public static void main(String... args) {
    var dive = new Dive();

    dive.solve("testInput.txt", new DiveParserOne());
    dive.solve("input.txt", new DiveParserOne());

    dive.solve("testInput.txt", new DiveParserTwo());
    dive.solve("input.txt", new DiveParserTwo());

  }

  void solve(final String commandsFile, DiveParser diveParser) {
    try (Stream<String> commands = Files.lines(Paths.get(Dive.class.getClassLoader().getResource(commandsFile).toURI()))) {
      commands.forEach(diveParser::parse);
      diveParser.printResultTo(out);
    } catch (URISyntaxException | IOException e) {
      err.println("Error");
      e.printStackTrace();
    }
  }

  private interface DiveParser {
    void parse(final String command);

    void printResultTo(final PrintStream out);
  }

  private static class DiveParserOne implements DiveParser {
    protected long forward = 0;
    protected long depth = 0;

    @Override
    public void parse(final String command) {
      var order = command.split(" ");
      switch (order[0]) {
        case "forward" -> forward += Long.parseLong(order[1]);
        case "down" -> depth += Long.parseLong(order[1]);
        case "up" -> depth -= Long.parseLong(order[1]);
        default -> err.println("Order not implemented");
      }
    }

    @Override
    public void printResultTo(final PrintStream out) {
      out.printf("Result: %d%n", forward * depth);
    }
  }

  private static class DiveParserTwo extends DiveParserOne {
    private long aim = 0;

    @Override
    public void parse(final String command) {
      var order = command.split(" ");
      switch (order[0]) {
        case "forward" -> {
          forward += Long.parseLong(order[1]);
          depth += Long.parseLong(order[1]) * aim;
        }
        case "down" -> aim += Long.parseLong(order[1]);
        case "up" -> aim -= Long.parseLong(order[1]);
        default -> err.println("Order not implemented");
      }
    }
  }
}
