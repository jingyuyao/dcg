package com.dcg.api;

import java.util.List;

public class RoomView {
  public final List<String> playerNames;
  public final boolean initialized;
  public final boolean isGameOver;

  public RoomView(List<String> playerNames, boolean initialized, boolean isGameOver) {
    this.playerNames = playerNames;
    this.initialized = initialized;
    this.isGameOver = isGameOver;
  }
}
