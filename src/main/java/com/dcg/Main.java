package com.dcg;

import com.dcg.game.Game;
import com.dcg.game.Message;
import com.dcg.game.Message.MessageType;
import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Game game = new Game();
    while (!game.isOver()) {
      System.out.print("> ");
      String input = scanner.nextLine();
      Optional<Message> message = parse(input);
      if (message.isPresent()) {
        game.handleMessage(message.get());
      } else {
        System.out.println("Unknown input: " + input);
      }
    }
    System.out.println("goodbye");
  }

  private static Optional<Message> parse(String input) {
    try {
      String[] tokenized = input.split(" ");
      MessageType messageType = MessageType.valueOf(tokenized[0].toUpperCase());
      return Optional.of(
          new Message(messageType, Arrays.copyOfRange(tokenized, 1, tokenized.length)));
    } catch (RuntimeException e) {
      return Optional.empty();
    }
  }
}
