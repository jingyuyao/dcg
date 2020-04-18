package com.dcg.source;

import com.dcg.command.Input;
import com.dcg.command.Target;
import java.util.function.BiFunction;

/**
 * A function to select the target of the command given originating source entity and input. Result
 * will be automatically memorized by the command. Instances will be injected.
 */
public interface TargetSource extends BiFunction<Integer, Input, Target> {}
