package com.dcg.api.legacy;

import com.dcg.battle.Unit;
import com.dcg.game.Common;
import java.util.ArrayList;
import java.util.List;

public class UnitView extends EntityView {
  public final int strength;
  public final int defense;
  public final List<String> attributes = new ArrayList<>();
  public final List<ActionView> actions;

  public UnitView(int id, Common common, Unit unit, List<ActionView> actions) {
    super(id, common);
    this.strength = unit.strength;
    this.defense = unit.defense;
    this.actions = actions;
    if (unit.flying) {
      attributes.add("Flying");
    }
    if (unit.lifeSteal) {
      attributes.add("Lifesteal");
    }
    if (unit.berserk) {
      attributes.add("Berserk");
    }
    if (unit.endurance) {
      attributes.add("Endurance");
    }
    if (unit.unblockable) {
      attributes.add("Unblockable");
    }
  }
}
