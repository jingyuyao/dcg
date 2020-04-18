package com.dcg.condition;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.battle.Unit;
import com.dcg.game.CoreSystem;

public class MinAnyUnitStrength implements WorldCondition {
  private final int strength;
  protected ComponentMapper<Unit> mUnit;

  public MinAnyUnitStrength(int strength) {
    this.strength = strength;
  }

  @Override
  public boolean test(CoreSystem coreSystem) {
    return coreSystem
        .getCurrentPlayerEntity()
        .flatMap(playerEntity -> coreSystem.getDescendants(playerEntity, Aspect.all(Unit.class)))
        .mapToObj(mUnit::get)
        .anyMatch(unit -> unit.strength >= strength);
  }
}
