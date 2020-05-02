package com.dcg.api;

import java.util.List;

public class GameView {
  public final List<PlayerView> players;
  public final List<CardView> forgeRow;
  public final List<CardView> throneDeck;
  public final List<CardView> mercenaryDeck;
  public final List<CardView> playArea;
  public final List<CardView> hand;
  public final List<CardView> discardPile;
  public final List<UnitView> attackingUnits;
  public final List<UnitView> defendingUnits;
  public final List<ExecutionView> recentExecutions;

  public GameView(
      List<PlayerView> players,
      List<CardView> forgeRow,
      List<CardView> throneDeck,
      List<CardView> mercenaryDeck,
      List<CardView> playArea,
      List<CardView> hand,
      List<CardView> discardPile,
      List<UnitView> attackingUnits,
      List<UnitView> defendingUnits,
      List<ExecutionView> recentExecutions) {
    this.players = players;
    this.forgeRow = forgeRow;
    this.throneDeck = throneDeck;
    this.mercenaryDeck = mercenaryDeck;
    this.playArea = playArea;
    this.hand = hand;
    this.discardPile = discardPile;
    this.attackingUnits = attackingUnits;
    this.defendingUnits = defendingUnits;
    this.recentExecutions = recentExecutions;
  }
}
