package com.dcg.server;

import java.util.Optional;
import org.java_websocket.WebSocket;

public class Attachment {
  private String name;
  private GameRoom gameRoom;

  public static Attachment get(WebSocket socket) {
    return socket.getAttachment();
  }

  public Optional<String> getName() {
    return Optional.ofNullable(name);
  }

  public void setName(String name) {
    this.name = name;
  }

  public Optional<GameRoom> getGameRoom() {
    return Optional.ofNullable(gameRoom);
  }

  public void setGameRoom(GameRoom gameRoom) {
    this.gameRoom = gameRoom;
  }
}
