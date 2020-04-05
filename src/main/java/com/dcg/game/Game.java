package com.dcg.game;

import com.dcg.board.Board;
import com.dcg.turn.AdvanceTurn;

public class Game {
  private final Board board = new Board(new String[] {"Alice", "Bob"});
  private boolean gameOver = false;

  public void process(String input) {
    if (input == null) return;

    switch (input) {
      case "quit":
        gameOver = true;
        break;
      case "advance":
        board.process(new AdvanceTurn());
        break;
    }
  }

  public boolean isOver() {
    return gameOver;
  }
}
