package com.dcg.triggercondition;

import java.util.List;

public class MinAllowedTargets implements TriggerCondition {
  private final int min;

  public MinAllowedTargets(int min) {
    this.min = min;
  }

  @Override
  public boolean test(int originEntity, List<Integer> allowedTargets) {
    return allowedTargets.size() >= min;
  }
}
