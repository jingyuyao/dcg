package com.dcg.api;

import com.dcg.card.Card;
import com.dcg.card.HasUnit;
import com.dcg.game.Common;
import java.util.List;

public class CardView extends EntityView {
  public final int cost;
  public final boolean canFlash;
  public final String kind;
  public final List<String> colors;
  public final int strength;
  public final List<ActionView> actions;

  public CardView(
      int id,
      Common common,
      Card card,
      String kind,
      List<String> colors,
      HasUnit hasUnit,
      List<ActionView> actions) {
    super(id, common);
    this.cost = card.cost;
    this.canFlash = card.canFlash;
    this.kind = kind;
    this.colors = colors;
    this.strength = hasUnit != null ? hasUnit.strength : 0;
    this.actions = actions;
  }
}
