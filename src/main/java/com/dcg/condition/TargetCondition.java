package com.dcg.condition;

import com.dcg.target.Target;
import java.util.function.Consumer;

/**
 * Precondition for a target. Implementations should throw with a helpful error message if the
 * condition fails. Instances will be injected.
 */
public interface TargetCondition extends Consumer<Target> {}
