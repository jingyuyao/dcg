package com.dcg.api;

import java.util.List;

public class WorldView {
  public final List<PlayerView> players;
  public final List<CardView> forgeRow;
  public final List<CardView> throneDeck;
  public final List<CardView> mercenaryDeck;
  public final List<CardView> playArea;

  public WorldView(
      List<PlayerView> players,
      List<CardView> forgeRow,
      List<CardView> throneDeck,
      List<CardView> mercenaryDeck,
      List<CardView> playArea) {
    this.players = players;
    this.forgeRow = forgeRow;
    this.throneDeck = throneDeck;
    this.mercenaryDeck = mercenaryDeck;
    this.playArea = playArea;
  }
}
