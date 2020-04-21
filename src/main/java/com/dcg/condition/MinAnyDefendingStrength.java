package com.dcg.condition;

import com.artemis.ComponentMapper;
import com.dcg.battle.Unit;
import com.dcg.game.CoreSystem;

public class MinAnyDefendingStrength implements TriggerCondition {
  private final int strength;
  protected ComponentMapper<Unit> mUnit;

  public MinAnyDefendingStrength(int strength) {
    this.strength = strength;
  }

  @Override
  public boolean test(CoreSystem coreSystem) {
    return coreSystem
        .getDefendingEntities()
        .mapToObj(mUnit::get)
        .anyMatch(unit -> unit.strength >= strength);
  }
}
