package com.dcg.server;

import com.dcg.api.RoomView;
import com.dcg.api.ServerMessage;
import com.dcg.game.Game;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.java_websocket.WebSocket;

/**
 * Contains an instance of {@link com.dcg.game.Game} and the {@link org.java_websocket.WebSocket}
 * connections to that game instance.
 */
public class GameRoom {
  private static final int REQUIRED_PLAYER_COUNT = 2;
  private final Gson gson = new Gson();
  private final List<WebSocket> connections = new ArrayList<>();
  private Game game;

  public void join(WebSocket conn, String playerName) {
    // TODO: allow reconnect if playerName match an existing player and they are disconnected
    if (isGameInProgress()) {
      System.out.println("Session: Game has already begun");
      return;
    }
    conn.setAttachment(new Attachment(playerName));
    connections.add(conn);
    if (connections.size() == REQUIRED_PLAYER_COUNT) {
      game = new Game(getConnectionNames());
      broadcastRoomView();
      broadcastWorldView();
    } else {
      broadcastRoomView();
    }
  }

  public void disconnected(WebSocket conn) {
    connections.remove(conn);
    if (connections.isEmpty()) {
      game = null;
    } else {
      broadcastRoomView();
    }
  }

  public void execute(WebSocket conn, List<Integer> args) {
    if (game == null) {
      System.out.println("Session: Game is not started yet.");
      return;
    }

    Attachment attachment = conn.getAttachment();
    if (attachment == null) {
      return;
    }

    game.execute(attachment.name, args);

    if (game.isOver()) {
      game = null;
      broadcastRoomView();
    } else {
      broadcastWorldView();
    }
  }

  public RoomView getRoomView() {
    return new RoomView(getConnectionNames(), isGameInProgress());
  }

  private void broadcastRoomView() {
    for (WebSocket conn : connections) {
      conn.send(gson.toJson(new ServerMessage("room-view", getRoomView())));
    }
  }

  private void broadcastWorldView() {
    for (WebSocket conn : connections) {
      conn.send(gson.toJson(new ServerMessage("world-view", game.getWorldView())));
    }
  }

  private List<String> getConnectionNames() {
    return connections.stream()
        .map(
            c -> {
              Attachment attachment = c.getAttachment();
              return attachment.name;
            })
        .collect(Collectors.toList());
  }

  private boolean isGameInProgress() {
    return game != null;
  }

  private static class Attachment {
    private final String name;

    private Attachment(String name) {
      this.name = name;
    }
  }
}
