package com.aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.System.err;
import static java.lang.System.out;

public class TreacheryOfWhales {

    private TreacheryOfWhales() {
    }

    public static void main(String... args) {
        var treacheryOfWhales = new TreacheryOfWhales();

        out.printf("Result: %d%n", treacheryOfWhales.solveOne("testInput.txt"));
        out.printf("Result: %d%n", treacheryOfWhales.solveOne("input.txt"));

        out.printf("Result: %d%n", treacheryOfWhales.solveTwo("testInput.txt"));
        out.printf("Result: %d%n", treacheryOfWhales.solveTwo("input.txt"));
    }

    long solveOne(final String inputFile) {
        try (Stream<String> input = Files.lines(Paths.get(TreacheryOfWhales.class.getClassLoader().getResource(inputFile).toURI()))) {
            var inputLines = input.toList();
            var crabs = Arrays.stream(inputLines.get(0).split(",")).map(Integer::valueOf).toList();
            return alignCrabs(crabs);
        } catch (URISyntaxException | IOException e) {
            err.println("Error");
            e.printStackTrace();
        }
        return -1;
    }

    private long alignCrabs(List<Integer> crabs) {
        var sort = crabs.stream().sorted().toList();
        var med = sort.get(sort.size() / 2);
        var fuel = 0;
        for (var crab : crabs) {
            fuel += Math.abs(crab - med);
        }
        return fuel;
    }

    long solveTwo(final String inputFile) {
        try (Stream<String> input = Files.lines(Paths.get(TreacheryOfWhales.class.getClassLoader().getResource(inputFile).toURI()))) {
            var inputLines = input.toList();
            var crabs = Arrays.stream(inputLines.get(0).split(",")).map(Integer::valueOf).toList();
            return alignCrabsTwo(crabs);
        } catch (URISyntaxException | IOException e) {
            err.println("Error");
            e.printStackTrace();
        }
        return -1;
    }

    private long alignCrabsTwo(List<Integer> crabs) {
        var mean = crabs.stream().mapToDouble(value -> value).average().getAsDouble();
        var fuel = 0;
        for (var crab : crabs) {
            var diff = Math.abs(crab - mean);
            fuel += diff * (diff + 1) / 2;
        }
        return fuel;
    }
}
