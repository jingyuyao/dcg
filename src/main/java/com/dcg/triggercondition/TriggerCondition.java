package com.dcg.triggercondition;

import java.util.function.Predicate;

/** A predicate that tests the world for the given origin entity. Instances will be injected. */
public interface TriggerCondition extends Predicate<Integer> {}
