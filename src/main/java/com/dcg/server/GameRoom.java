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
  private final List<WebSocket> joined = new ArrayList<>();
  private Game game;

  public void join(WebSocket socket) {
    // TODO: allow reconnect if playerName match an existing player and they are disconnected
    if (isGameInProgress()) {
      System.out.println("Session: Game has already begun");
      return;
    }
    Attachment attachment = Attachment.get(socket);
    Optional<String> name = attachment.getName();
    if (!name.isPresent()) {
      System.out.println("Session: Need a name to join");
      return;
    }

    attachment.setGameRoom(this);
    joined.add(socket);
    broadcastRoomView();
  }

  public void leave(WebSocket socket) {
    joined.remove(socket);
    Attachment.get(socket).setGameRoom(null);
    if (joined.isEmpty()) {
      game = null;
    }
    broadcastRoomView();
  }

  public void start(WebSocket socket) {
    game = new Game(getPlayerNames());
    broadcastRoomView();
    broadcastGameView();
  }

  public void execute(WebSocket socket, List<Integer> args) {
    if (game == null) {
      System.out.println("Session: Game is not started yet.");
      return;
    }

    Optional<String> name = Attachment.get(socket).getName();
    if (!name.isPresent()) {
      System.out.println("Session: Player needs a name");
      return;
    }

    game.execute(name.get(), args);

    if (game.isOver()) {
      game = new Game(getPlayerNames());
    }
    broadcastGameView();
  }

  public List<String> getPlayerNames() {
    return joined.stream()
        .map(c -> Attachment.get(c).getName().orElse(""))
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
      String playerName = Attachment.get(socket).getName().orElse("");
      Util.send(socket, Kind.GAME_VIEW, game.getGameView(playerName));
    }
  }

  public String getRoomName() {
    return "default";
  }
}
