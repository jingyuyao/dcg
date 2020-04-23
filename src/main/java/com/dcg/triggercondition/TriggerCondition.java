package com.dcg.triggercondition;

import java.util.List;

/**
 * A predicate that tests the world for the given origin entity and currently allowed targets.
 * Instances will be injected.
 */
@FunctionalInterface
public interface TriggerCondition {
  boolean test(int originEntity, List<Integer> allowedTargets);
}
