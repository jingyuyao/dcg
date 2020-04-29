package com.dcg.api;

import java.util.List;

public class RoomView {
  public final List<String> playerNames;
  public final boolean isGameInProgress;

  public RoomView(List<String> playerNames, boolean isGameInProgress) {
    this.playerNames = playerNames;
    this.isGameInProgress = isGameInProgress;
  }
}
