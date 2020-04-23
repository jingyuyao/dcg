package com.dcg.triggercondition;

import com.dcg.game.CoreSystem;

public class MinUnitCount implements TriggerCondition {
  private final int count;
  protected CoreSystem coreSystem;

  public MinUnitCount(int count) {
    this.count = count;
  }

  @Override
  public boolean test(Integer originEntity) {
    return coreSystem.getDefendingEntities().count() >= count;
  }
}
