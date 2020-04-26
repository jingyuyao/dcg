package com.dcg.server;

public class ServerMessage {
  public final String kind;
  public final Object data;

  public ServerMessage(String kind, Object data) {
    this.kind = kind;
    this.data = data;
  }
}
