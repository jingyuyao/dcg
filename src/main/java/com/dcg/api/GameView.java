package com.dcg.api;

import java.util.List;

public class GameView {
  public final int playerId;
  public final int currentPlayerId;
  public final int previousPlayerId;
  public final List<PlayerView> players;
  public final List<CardView> cards;
  public final List<UnitView> units;

  public GameView(
      int playerId,
      int currentPlayerId,
      int previousPlayerId,
      List<PlayerView> players,
      List<CardView> cards,
      List<UnitView> units) {
    this.playerId = playerId;
    this.currentPlayerId = currentPlayerId;
    this.previousPlayerId = previousPlayerId;
    this.players = players;
    this.cards = cards;
    this.units = units;
  }
}
