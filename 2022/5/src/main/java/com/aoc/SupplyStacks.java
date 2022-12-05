package com.aoc;

import static java.lang.System.err;
import static java.lang.System.out;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SupplyStacks {

  private SupplyStacks() {
  }

  public static void main(final String... args) {
    out.printf("Result: %s%n", new SupplyStacks().solveOne("testInput.txt", 3));
    out.printf("Result: %s%n", new SupplyStacks().solveOne("input.txt", 9));

    out.printf("Result: %s%n", new SupplyStacks().solveTwo("testInput.txt", 3));
    out.printf("Result: %s%n", new SupplyStacks().solveTwo("input.txt", 9));
  }

  private String solveOne(String inputFile, int totalStacks) {
    var stacks = new ArrayList<Stack<Character>>(totalStacks);
    for (var index = 0; index < totalStacks; index++) {
      stacks.add(new Stack<>());
    }
    var firstTime = true;
    try (Stream<String> input = Files.lines(
        Paths.get(SupplyStacks.class.getClassLoader().getResource(inputFile).toURI()))) {
      var inputList = input.toList();
      for (var il : inputList) {
        var pattern = "(    )|(\\[[A-Z]\\] )|(\\[[A-Z]\\])|(   )";
        var r = Pattern.compile(pattern);
        var m = r.matcher(il);

        if (m.find()) {
          for (var index = 0; index < totalStacks; index++) {
            var stacked = m.group(0);
            if (!stacked.isBlank()) {
              stacks.get(index).add(stacked.charAt(1));
            }
            if (!m.find()) {
              break;
            }
          }
        }

        pattern = "move ([0-9]*) from ([0-9]*) to ([0-9]*)";
        r = Pattern.compile(pattern);
        m = r.matcher(il);

        if (m.find()) {
          if (firstTime) {
            firstTime = false;
            for (var stack : stacks) {
              var tmp1 = new Stack<Character>();
              while (!stack.empty()) {
                tmp1.push(stack.pop());
              }
              var tmp2 = new Stack<Character>();
              while (!tmp1.empty()) {
                tmp2.push(tmp1.pop());
              }
              while (!tmp2.empty()) {
                stack.push(tmp2.pop());
              }
            }
          }
          var moves = m.group(1);
          var init = m.group(2);
          var end = m.group(3);
          for (var movements = 0; movements < Integer.parseInt(moves); movements++) {
            stacks.get(Integer.parseInt(end) - 1).push(stacks.get(Integer.parseInt(init) - 1).pop());
          }
        }
      }
    } catch (URISyntaxException |
             IOException e) {
      err.println("Error");
      e.printStackTrace();
    }
    return stacks.stream().map(Stack::peek).collect(Collectors.toList()).toString();
  }

  private String solveTwo(String inputFile, int totalStacks) {
    var stacks = new ArrayList<Stack<Character>>(totalStacks);
    for (var index = 0; index < totalStacks; index++) {
      stacks.add(new Stack<>());
    }
    var firstTime = true;
    try (Stream<String> input = Files.lines(
        Paths.get(SupplyStacks.class.getClassLoader().getResource(inputFile).toURI()))) {
      var inputList = input.toList();
      for (var il : inputList) {
        var pattern = "(    )|(\\[[A-Z]\\] )|(\\[[A-Z]\\])|(   )";
        var r = Pattern.compile(pattern);
        var m = r.matcher(il);

        if (m.find()) {
          for (var index = 0; index < totalStacks; index++) {
            var stacked = m.group(0);
            if (!stacked.isBlank()) {
              stacks.get(index).add(stacked.charAt(1));
            }
            if (!m.find()) {
              break;
            }
          }
        }

        pattern = "move ([0-9]*) from ([0-9]*) to ([0-9]*)";
        r = Pattern.compile(pattern);
        m = r.matcher(il);

        if (m.find()) {
          if (firstTime) {
            firstTime = false;
            for (var stack : stacks) {
              var tmp1 = new Stack<Character>();
              while (!stack.empty()) {
                tmp1.push(stack.pop());
              }
              var tmp2 = new Stack<Character>();
              while (!tmp1.empty()) {
                tmp2.push(tmp1.pop());
              }
              while (!tmp2.empty()) {
                stack.push(tmp2.pop());
              }
            }
          }
          var moves = m.group(1);
          var init = m.group(2);
          var end = m.group(3);
          var tmp = new Stack<Character>();
          for (var movements = 0; movements < Integer.parseInt(moves); movements++) {
            tmp.push(stacks.get(Integer.parseInt(init) - 1).pop());
          }
          for (var movements = 0; movements < Integer.parseInt(moves); movements++) {
            stacks.get(Integer.parseInt(end) - 1).push(tmp.pop());
          }
        }
      }
    } catch (URISyntaxException |
             IOException e) {
      err.println("Error");
      e.printStackTrace();
    }
    return stacks.stream().map(Stack::peek).collect(Collectors.toList()).toString();
  }
}
