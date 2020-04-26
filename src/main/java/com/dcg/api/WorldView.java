package com.dcg.api;

import java.util.List;

public class WorldView {
  public final List<PlayerView> players;

  public WorldView(List<PlayerView> players) {
    this.players = players;
  }
}
