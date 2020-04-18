package com.dcg.condition;

import com.dcg.game.CoreSystem;

public class MinUnitCount implements WorldCondition {
  private final int count;

  public MinUnitCount(int count) {
    this.count = count;
  }

  @Override
  public boolean test(CoreSystem coreSystem) {
    return coreSystem.getDefendingEntities().count() >= count;
  }
}
