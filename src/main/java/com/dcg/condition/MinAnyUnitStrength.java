package com.dcg.condition;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.battle.Unit;
import com.dcg.game.CoreSystem;
import java.util.function.BooleanSupplier;

public class MinAnyUnitStrength implements BooleanSupplier {
  private final int strength;
  protected CoreSystem coreSystem;
  protected ComponentMapper<Unit> mUnit;

  public MinAnyUnitStrength(int strength) {
    this.strength = strength;
  }

  @Override
  public boolean getAsBoolean() {
    return coreSystem
        .getCurrentPlayerEntity()
        .flatMap(playerEntity -> coreSystem.getDescendants(playerEntity, Aspect.all(Unit.class)))
        .mapToObj(mUnit::get)
        .anyMatch(unit -> unit.strength >= strength);
  }
}
