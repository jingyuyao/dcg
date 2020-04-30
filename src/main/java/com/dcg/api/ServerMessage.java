package com.dcg.api;

public class ServerMessage {
  private final Kind kind;
  private final Object data;

  public ServerMessage(Kind kind, Object data) {
    this.kind = kind;
    this.data = data;
  }

  public enum Kind {
    ATTACHMENT_VIEW,
    ROOM_LIST,
    GAME_ROOM_VIEW,
    GAME_VIEW,
  }
}
