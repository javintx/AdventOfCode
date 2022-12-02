package com.aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import static java.lang.System.out;

public class SonarSweep {
  private SonarSweep() {
  }

  public static void main(String... args) {
    var sonarSweep = new SonarSweep();

    sonarSweep.solve(loadFirstMeasurements("testInput.txt"));
    sonarSweep.solve(loadFirstMeasurements("input.txt"));

    sonarSweep.solve(loadSecondMeasurements("testInput.txt"));
    sonarSweep.solve(loadSecondMeasurements("input.txt"));
  }

  private static Integer[] loadFirstMeasurements(final String measurementFile) {
    try (Stream<String> lines = Files.lines(Paths.get(SonarSweep.class.getClassLoader().getResource(measurementFile).toURI()))) {
      var data = lines.map(Integer::valueOf).toList();
      var array = new Integer[data.size()];
      return data.toArray(array);
    } catch (URISyntaxException | IOException e) {
      return new Integer[]{};
    }
  }

  private static Integer[] loadSecondMeasurements(final String measurementFile) {
    try (Stream<String> lines = Files.lines(Paths.get(SonarSweep.class.getClassLoader().getResource(measurementFile).toURI()))) {
      var linesRead = lines.map(Integer::valueOf).toList();
      var data = new ArrayList<Integer>();
      for (int i = 0; i < linesRead.size() - 2; i++) {
        data.add(linesRead.get(i) + linesRead.get(i + 1) + linesRead.get(i + 2));
      }
      var array = new Integer[data.size()];
      return data.toArray(array);
    } catch (URISyntaxException | IOException e) {
      return new Integer[]{};
    }
  }

  void solve(final Integer[] measurements) {
    Integer previousMeasurement = null;
    var increasedMeasurements = 0;
    for (var measurement : measurements) {
      if (isMeasurable(previousMeasurement, measurement)) {
        increasedMeasurements++;
      }
      previousMeasurement = measurement;
    }
    out.printf("Increased measurements: %d%n", increasedMeasurements);
  }

  private boolean isMeasurable(Integer previousMeasurement, Integer measurement) {
    return previousMeasurement != null && previousMeasurement < measurement;
  }

}
