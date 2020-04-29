package com.dcg.server;

import com.dcg.api.RoomView;
import com.dcg.api.WorldView;
import com.dcg.game.Game;
import java.util.ArrayList;
import java.util.List;
import org.java_websocket.WebSocket;

/**
 * Contains an instance of {@link com.dcg.game.Game} and the {@link org.java_websocket.WebSocket}
 * connections to that game instance.
 */
public class GameRoom {
  private static final int REQUIRED_PLAYER_COUNT = 2;
  private final List<String> playerNames = new ArrayList<>();
  private final Game game = new Game();
  private boolean initialized = false;
  private boolean gameOver = false;

  public void join(WebSocket conn, String playerName) {
    if (initialized) {
      System.out.println("Session: Game has already initialized");
      return;
    }
    playerNames.add(playerName);
    conn.setAttachment(new PlayerAttachment(playerName));
    if (playerNames.size() == REQUIRED_PLAYER_COUNT) {
      initialized = true;
      game.setUp(playerNames);
    }
  }

  public void execute(WebSocket conn, List<Integer> args) {
    if (!initialized) {
      System.out.println("Session: Game is not started yet.");
      return;
    }

    if (gameOver) {
      System.out.println("Session: Game is already over.");
    }

    PlayerAttachment attachment = conn.getAttachment();
    if (attachment == null) {
      return;
    }

    game.execute(attachment.name, args);

    if (game.isOver()) {
      gameOver = true;
    }
  }

  public RoomView getRoomView() {
    return new RoomView(playerNames, initialized, gameOver);
  }

  public WorldView getWorldView() {
    return game.getWorldView();
  }

  public boolean isInitialized() {
    return initialized;
  }

  public boolean isGameOver() {
    return gameOver;
  }

  private static class PlayerAttachment {
    private final String name;

    private PlayerAttachment(String name) {
      this.name = name;
    }
  }
}
