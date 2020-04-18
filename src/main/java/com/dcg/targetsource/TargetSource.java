package com.dcg.targetsource;

import com.dcg.command.Input;
import com.dcg.command.Target;
import java.util.function.BiFunction;

// TODO: move max target count here so its overridable, make this an abstract class with
// setMaxTargetCount
/**
 * A function to select target given originating source entity and input. Result will be
 * automatically memorized by the command. Instances will be injected.
 */
public interface TargetSource extends BiFunction<Integer, Input, Target> {}
