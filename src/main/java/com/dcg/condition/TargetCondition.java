package com.dcg.condition;

import java.util.List;
import java.util.function.Predicate;

/** A predicate that tests a list of targets. Instances will be injected. */
public interface TargetCondition extends Predicate<List<Integer>> {}
