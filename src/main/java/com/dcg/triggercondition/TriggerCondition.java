package com.dcg.triggercondition;

import java.util.List;

// TODO: fix conditions in the package so they make use of allowedTargets
/**
 * A predicate that tests the world for the given origin entity and currently allowed targets.
 * Instances will be injected.
 */
@FunctionalInterface
public interface TriggerCondition {
  boolean test(int originEntity, List<Integer> allowedTargets);
}
