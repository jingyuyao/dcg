package com.dcg.api;

import com.dcg.card.Card;
import com.dcg.game.Common;
import java.util.List;

public class CardView extends EntityView {
  public final int cost;
  public final boolean canWrap;
  public final CardKind kind;
  public final List<String> colors;
  public final int strength;
  public final List<ActionView> actions;

  public CardView(
      int id,
      Common common,
      Card card,
      CardKind kind,
      List<String> colors,
      int strength,
      List<ActionView> actions) {
    super(id, common);
    this.cost = card.cost;
    this.canWrap = card.canWarp;
    this.kind = kind;
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
}
