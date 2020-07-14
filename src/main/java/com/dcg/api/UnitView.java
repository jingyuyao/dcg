package com.dcg.api;

import com.dcg.api.CardView.CardColor;
import com.dcg.battle.Unit;
import com.dcg.game.Common;
import java.util.ArrayList;
import java.util.List;

public class UnitView extends EntityView {
  public final int ownerEntity;
  public final int cardEntity;
  public final UnitState state;
  public final boolean isToken;
  public final int strength;
  public final int defense;
  public final List<CardColor> colors;
  public final List<UnitAttribute> attributes = new ArrayList<>();
  public final List<ActionView> actions;

  public UnitView(
      int id,
      Common common,
      int ownerEntity,
      Unit unit,
      UnitState state,
      List<CardColor> colors,
      List<ActionView> actions) {
    super(id, common);
    this.ownerEntity = ownerEntity;
    this.cardEntity = unit.cardEntity;
    this.isToken = unit.isToken;
    this.strength = unit.strength;
    this.defense = unit.defense;
    this.state = state;
    this.colors = colors;
    this.actions = actions;
    if (unit.flying) {
      attributes.add(UnitAttribute.FLYING);
    }
    if (unit.lifeSteal) {
      attributes.add(UnitAttribute.LIFESTEAL);
    }
    if (unit.berserk) {
      attributes.add(UnitAttribute.BERSERK);
    }
    if (unit.endurance) {
      attributes.add(UnitAttribute.ENDURANCE);
    }
    if (unit.unblockable) {
      attributes.add(UnitAttribute.UNBLOCKABLE);
    }
  }

  public enum UnitState {
    ATTACKING,
    DEFENDING,
  }

  public enum UnitAttribute {
    FLYING,
    LIFESTEAL,
    BERSERK,
    ENDURANCE,
    UNBLOCKABLE,
  }
}
