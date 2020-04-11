package com.dcg;

import com.dcg.game.Game;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Game game = new Game();
    while (!game.isOver()) {
      System.out.print("> ");
      String input = scanner.nextLine();
      Optional<List<Integer>> message = parse(input);
      if (message.isPresent()) {
        game.handleInput(message.get());
      } else {
        System.out.printf("Unable to parse input: %s\n", input);
      }
    }
    System.out.println("goodbye");
  }

  private static Optional<List<Integer>> parse(String input) {
    try {
      String[] tokenized = input.split(" ");
      return Optional.of(
          Arrays.stream(tokenized).map(Integer::parseInt).collect(Collectors.toList()));
    } catch (RuntimeException e) {
      return Optional.empty();
    }
  }
}
