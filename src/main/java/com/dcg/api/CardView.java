package com.dcg.api;

import com.dcg.card.Card;
import com.dcg.game.Common;
import java.util.List;

public class CardView extends EntityView {
  public final int cost;
  public final boolean canWrap;
  public final CardKind kind;
  public final CardLocation location;
  public final List<String> colors;
  public final int strength;
  public final List<ActionView> actions;

  public CardView(
      int id,
      Common common,
      Card card,
      CardKind kind,
      CardLocation location,
      List<String> colors,
      int strength,
      List<ActionView> actions) {
    super(id, common);
    this.cost = card.cost;
    this.canWrap = card.canWarp;
    this.kind = kind;
    this.location = location;
    this.colors = colors;
    this.strength = strength;
    this.actions = actions;
  }

  public enum CardKind {
    UNKNOWN,
    BASIC,
    SPELL,
    UNIT
  }

  public enum CardLocation {
    UNKNOWN,
    FORGE_DECK,
    FORGE_ROW,
    THRONE_DECK,
    MERCENARY_DECK,
    PLAYER_DECK,
    DISCARD_PILE,
    HAND,
    PLAY_AREA,
  }
}
