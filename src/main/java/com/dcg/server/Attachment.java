package com.dcg.server;

import java.util.Optional;
import org.java_websocket.WebSocket;

public class Attachment {
  private String playerName;
  private String rejoinToken;
  private GameRoom gameRoom;
  private int playerEntity = -1;

  public static Attachment get(WebSocket socket) {
    return socket.getAttachment();
  }

  public Optional<String> getPlayerName() {
    return Optional.ofNullable(playerName);
  }

  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  public Optional<String> getRejoinToken() {
    return Optional.ofNullable(rejoinToken);
  }

  public void setRejoinToken(String rejoinToken) {
    this.rejoinToken = rejoinToken;
  }

  public Optional<GameRoom> getGameRoom() {
    return Optional.ofNullable(gameRoom);
  }

  public void setGameRoom(GameRoom gameRoom) {
    this.gameRoom = gameRoom;
  }

  public int getPlayerEntity() {
    return playerEntity;
  }

  public void setPlayerEntity(int playerEntity) {
    this.playerEntity = playerEntity;
  }
}
