package com.dcg.triggercondition;

import com.artemis.ComponentMapper;
import com.dcg.battle.Unit;
import com.dcg.game.CoreSystem;

public class MinAnyDefendingStrength implements TriggerCondition {
  private final int strength;
  protected CoreSystem coreSystem;
  protected ComponentMapper<Unit> mUnit;

  public MinAnyDefendingStrength(int strength) {
    this.strength = strength;
  }

  @Override
  public boolean test(Integer originEntity) {
    return coreSystem
        .getDefendingEntities()
        .map(mUnit::get)
        .anyMatch(unit -> unit.strength >= strength);
  }
}
