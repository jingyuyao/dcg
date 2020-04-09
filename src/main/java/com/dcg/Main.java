package com.dcg;

import com.beust.jcommander.JCommander;
import com.dcg.game.Game;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Game game = new Game();
    while (!game.isOver()) {
      System.out.print("> ");
      String rawInput = scanner.nextLine();
      Input input = new Input();
      JCommander.newBuilder().addObject(input).build().parse(rawInput.split("\\s"));
      game.handleInput(input);
    }
    System.out.println("goodbye");
  }
}
