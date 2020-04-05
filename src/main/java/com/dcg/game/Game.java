package com.dcg.game;

import com.dcg.board.Board;
import com.dcg.player.AddPlayer;
import com.dcg.turn.AdvanceTurn;
import com.dcg.turn.InitTurn;

public class Game {
  private final Board board = new Board();
  private boolean gameOver = false;

  public Game() {
    board.process(new AddPlayer("Alice"));
    board.process(new AddPlayer("Bob"));
    board.process(new InitTurn("Alice"));
  }

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
