package com.dcg;

import com.dcg.game.Game;

import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Game game = new Game();
    while (!game.isOver()) {
      System.out.print("command: ");
      game.process(scanner.nextLine());
    }
    System.out.println("goodbye");
  }
}
