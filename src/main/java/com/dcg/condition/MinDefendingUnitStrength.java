package com.dcg.condition;

import com.artemis.ComponentMapper;
import com.dcg.battle.Unit;
import com.dcg.command.Target;
import com.dcg.game.CoreSystem;

public class MinDefendingUnitStrength implements TargetCondition {
  private final int strength;
  private final boolean onlySelf;
  protected CoreSystem coreSystem;
  protected ComponentMapper<Unit> mUnit;

  public MinDefendingUnitStrength(int strength, boolean onlySelf) {
    this.strength = strength;
    this.onlySelf = onlySelf;
  }

  @Override
  public boolean test(Target target) {
    return coreSystem
        .getDefendingEntities()
        .filter(unitEntity -> !onlySelf || unitEntity == target.getFrom())
        .mapToObj(mUnit::get)
        .anyMatch(unit -> unit.strength >= strength);
  }
}
