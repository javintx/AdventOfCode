package com.aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.err;
import static java.lang.System.out;

public class Lanternfish {

    private Lanternfish() {
    }

    public static void main(String... args) {
        var lanternfish = new Lanternfish();

        out.printf("Result: %d%n", lanternfish.solveOne("testInput.txt"));
        out.printf("Result: %d%n", lanternfish.solveOne("input.txt"));

        out.printf("Result: %d%n", lanternfish.solveTwo("testInput.txt"));
        out.printf("Result: %d%n", lanternfish.solveTwo("input.txt"));
    }

    long solveOne(final String inputFile) {
        try (Stream<String> input = Files.lines(Paths.get(Lanternfish.class.getClassLoader().getResource(inputFile).toURI()))) {
            var inputLines = input.toList();
            var fishes = Arrays.stream(inputLines.get(0).split(",")).map(Integer::valueOf).collect(Collectors.toList());
            return countLanternfish(fishes, 80);
        } catch (URISyntaxException | IOException e) {
            err.println("Error");
            e.printStackTrace();
        }
        return -1;
    }

    long solveTwo(final String inputFile) {
        try (Stream<String> input = Files.lines(Paths.get(Lanternfish.class.getClassLoader().getResource(inputFile).toURI()))) {
            var inputLines = input.toList();
            var fishes = Arrays.stream(inputLines.get(0).split(",")).map(Integer::valueOf).collect(Collectors.toList());
            return countLanternfish(fishes, 256);
        } catch (URISyntaxException | IOException e) {
            err.println("Error");
            e.printStackTrace();
        }
        return -1;
    }

    public long countLanternfish(List<Integer> fishes, int days) {
        long[] fish = new long[9];
        fishes.forEach(i -> fish[i]++);

        var newFish = 0L;
        for (int day = 0; day < days; day++) {
            newFish = fish[0];
            System.arraycopy(fish, 1, fish, 0, fish.length - 1);
            fish[6] += newFish;
            fish[8] = newFish;
        }

        return Arrays.stream(fish).sum();
    }

}
