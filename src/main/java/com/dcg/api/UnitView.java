package com.dcg.api;

import com.dcg.battle.Unit;
import com.dcg.game.Common;
import java.util.ArrayList;
import java.util.List;

public class UnitView extends EntityView {
  public final int ownerEntity;
  public final int cardEntity;
  public final UnitState state;
  public final int strength;
  public final int defense;
  public final List<UnitAttribute> attributes = new ArrayList<>();
  public final List<ActionView> actions;

  public UnitView(
      int id,
      Common common,
      int ownerEntity,
      Unit unit,
      UnitState state,
      List<ActionView> actions) {
    super(id, common);
    this.ownerEntity = ownerEntity;
    this.cardEntity = unit.cardEntity;
    this.strength = unit.strength;
    this.defense = unit.defense;
    this.state = state;
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
    UNKNOWN,
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
