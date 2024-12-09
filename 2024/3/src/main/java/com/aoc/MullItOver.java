package com.aoc;

import static java.lang.System.err;
import static java.lang.System.out;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class MullItOver {

  private MullItOver() {
  }

  public static void main(final String... args) {
//    out.printf("Result: %d%n", new MullItOver().solveOne("testInput.txt"));
//    out.printf("Result: %d%n", new MullItOver().solveOne("input.txt"));

    out.printf("Result: %d%n", new MullItOver().solveTwo("testInput2.txt"));
    out.printf("Result: %d%n", new MullItOver().solveTwo("input2.txt"));
  }

  private long solveOne(final String inputFile) {
    AtomicLong multiplyValue = new AtomicLong(0);
    try (Stream<String> input = Files.lines(
        Paths.get(MullItOver.class.getClassLoader().getResource(inputFile).toURI()))) {
      for (String command : input.toList()) {
        Matcher matcher = Pattern.compile("mul\\((\\d+),(\\d+)\\)").matcher(command);
        while (matcher.find()) {
          long num1 = Integer.parseInt(matcher.group(1));
          long num2 = Integer.parseInt(matcher.group(2));
          multiplyValue.updateAndGet(operand -> operand + (num1 * num2));
        }
      }
    } catch (URISyntaxException | IOException e) {
      err.println("Error");
      e.printStackTrace();
    }
    return multiplyValue.get();
  }

  private long solveTwo(String inputFile) {
    AtomicLong multiplyValue = new AtomicLong(0);
    try (Stream<String> input = Files.lines(
        Paths.get(MullItOver.class.getClassLoader().getResource(inputFile).toURI()))) {
      for (String command : input.toList()) {
        boolean end = false;
        while(!end) {
          var donts = command.indexOf("don't\\(\\)");
          var dos = command.indexOf("do\\(\\)");
          while (dos < donts) {
            Matcher matcher = Pattern.compile("mul\\((\\d+),(\\d+)\\)").matcher(command);
            while (matcher.find()) {
              long num1 = Integer.parseInt(matcher.group(1));
              long num2 = Integer.parseInt(matcher.group(2));
              multiplyValue.updateAndGet(operand -> operand + (num1 * num2));
            }
          }
          end = true;
        }
      }
    } catch (URISyntaxException | IOException e) {
      err.println("Error");
      e.printStackTrace();
    }
    return multiplyValue.get();
  }

}
