package com.dcg.api;

import com.dcg.card.Card;
import com.dcg.game.Common;
import java.util.List;

public class CardView extends EntityView {
  public final int ownerEntity;
  public final int cost;
  public final boolean canWrap;
  public final CardKind kind;
  public final CardLocation location;
  public final List<CardColor> colors;
  public final int strength;
  public final List<ActionView> actions;

  public CardView(
      int id,
      Common common,
      int ownerEntity,
      Card card,
      CardKind kind,
      CardLocation location,
      List<CardColor> colors,
      int strength,
      List<ActionView> actions) {
    super(id, common);
    this.ownerEntity = ownerEntity;
    this.cost = card.cost;
    this.canWrap = card.canWarp;
    this.kind = kind;
    this.location = location;
    this.colors = colors;
    this.strength = strength;
    this.actions = actions;
  }

  public enum CardKind {
    BASIC,
    SPELL,
    UNIT
  }

  public enum CardLocation {
    FORGE_DECK,
    FORGE_ROW,
    THRONE_DECK,
    MERCENARY_DECK,
    PLAYER_DECK,
    DISCARD_PILE,
    HAND,
    PLAY_AREA,
    BANISHED_PILE
  }

  public enum CardColor {
    RED,
    GREEN,
    BLUE,
    YELLOW,
    BLACK,
  }
}
