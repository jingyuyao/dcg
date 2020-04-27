package com.dcg.game;

public class Preconditions {
  public static void checkGameState(boolean assertion, String message, Object... args) {
    if (!assertion) {
      throw new GameStateException(message, args);
    }
  }

  public static class GameStateException extends RuntimeException {
    public GameStateException(String message, Object... args) {
      super(String.format(message, args));
    }
  }
}
