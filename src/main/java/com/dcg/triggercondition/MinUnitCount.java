package com.dcg.triggercondition;

import com.dcg.game.CoreSystem;
import java.util.List;

public class MinUnitCount implements TriggerCondition {
  private final int count;
  protected CoreSystem coreSystem;

  public MinUnitCount(int count) {
    this.count = count;
  }

  @Override
  public boolean test(int originEntity, List<Integer> allowedTargets) {
    return coreSystem.getDefendingEntities().count() >= count;
  }
}
