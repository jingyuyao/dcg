package com.dcg.api;

import java.util.List;

public class GameView {
  public final String currentPlayerName;
  public final String previousPlayerName;
  public final List<PlayerView> players;
  public final List<CardView> cards;
  public final List<UnitView> units;

  public GameView(
      String currentPlayerName,
      String previousPlayerName,
      List<PlayerView> players,
      List<CardView> cards,
      List<UnitView> units) {
    this.currentPlayerName = currentPlayerName;
    this.previousPlayerName = previousPlayerName;
    this.players = players;
    this.cards = cards;
    this.units = units;
  }
}
