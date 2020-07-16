package com.dcg.server;

import com.dcg.api.GameRoomView;
import com.dcg.api.ServerMessage.Kind;
import com.dcg.api.Util;
import com.dcg.game.Game;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.java_websocket.WebSocket;

/**
 * Contains an instance of {@link com.dcg.game.Game} and the {@link org.java_websocket.WebSocket}
 * connections to that game instance.
 */
public class GameRoom {
  private static final int MAX_PLAYER_COUNT = 4;
  private final String name;
  private final List<WebSocket> joined = new ArrayList<>();
  private Game game;

  public GameRoom(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void join(WebSocket socket, String playerName) {
    if (getJoinedPlayerNames().contains(playerName)) {
      System.out.println("Session: Player with the given name already joined");
    } else if (joined.size() >= MAX_PLAYER_COUNT) {
      System.out.println("Session: Max player count reached");
    } else {
      if (isGameInProgress()) {
        if (game.getPlayerNames().contains(playerName)) {
          System.out.println("Session: Rejoining");
          joinInternal(socket, playerName);
          broadcastGameView();
        } else {
          System.out.println("Session: Game has already begun");
        }
      } else {
        System.out.println("Session: Joining");
        joinInternal(socket, playerName);
      }
    }
  }

  private void joinInternal(WebSocket socket, String playerName) {
    Attachment attachment = Attachment.get(socket);
    attachment.setPlayerName(playerName);
    attachment.setGameRoom(this);
    joined.add(socket);
    broadcastRoomView();
  }

  public void leave(WebSocket socket) {
    joined.remove(socket);
    Attachment attachment = Attachment.get(socket);
    attachment.setPlayerName(null);
    attachment.setGameRoom(null);
    if (joined.isEmpty()) {
      game = null;
    }
    broadcastRoomView();
  }

  public void start(WebSocket socket) {
    if (joined.size() > 1) {
      game = new Game(getJoinedPlayerNames());
      broadcastRoomView();
      broadcastGameView();
    }
  }

  public void execute(WebSocket socket, List<Integer> args) {
    if (game == null) {
      System.out.println("Session: Game is not started yet.");
      return;
    }

    Optional<String> name = Attachment.get(socket).getPlayerName();
    if (!name.isPresent()) {
      System.out.println("Session: Player needs a name");
      return;
    }

    game.execute(name.get(), args);

    if (game.isOver()) {
      game = new Game(getJoinedPlayerNames());
    }
    broadcastGameView();
  }

  public List<String> getJoinedPlayerNames() {
    return joined.stream()
        .map(c -> Attachment.get(c).getPlayerName().orElse(""))
        .collect(Collectors.toList());
  }

  public boolean isGameInProgress() {
    return game != null;
  }

  private void broadcastRoomView() {
    for (WebSocket socket : joined) {
      Util.send(socket, Kind.GAME_ROOM_VIEW, new GameRoomView(this));
    }
  }

  private void broadcastGameView() {
    for (WebSocket socket : joined) {
      String playerName = Attachment.get(socket).getPlayerName().orElse("");
      Util.send(socket, Kind.GAME_VIEW, game.getGameView(playerName));
    }
  }
}
