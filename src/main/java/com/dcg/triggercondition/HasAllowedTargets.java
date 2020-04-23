package com.dcg.triggercondition;

import java.util.List;

public class HasAllowedTargets implements TriggerCondition {
  @Override
  public boolean test(int originEntity, List<Integer> allowedTargets) {
    return !allowedTargets.isEmpty();
  }
}
