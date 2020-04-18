package com.dcg.effect;

import com.artemis.Aspect;
import com.dcg.battle.Unit;
import com.dcg.game.CoreSystem;
import java.util.function.BooleanSupplier;

public class MinUnitCount implements BooleanSupplier {
  private final int count;
  protected CoreSystem coreSystem;

  public MinUnitCount(int count) {
    this.count = count;
  }

  @Override
  public boolean getAsBoolean() {
    long playerUnitCount =
        coreSystem
            .getCurrentPlayerEntity()
            .flatMap(
                playerEntity -> coreSystem.getDescendants(playerEntity, Aspect.all(Unit.class)))
            .count();
    return playerUnitCount >= count;
  }
}
