package com.dcg.condition;

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
    return coreSystem.getDefendingEntities().count() >= count;
  }
}
