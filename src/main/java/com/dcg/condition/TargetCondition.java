package com.dcg.condition;

import com.dcg.target.Target;
import java.util.function.Predicate;

/** A predicate that tests a target. Instances will be injected. */
public interface TargetCondition extends Predicate<Target> {}
