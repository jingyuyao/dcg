package com.dcg.source;

import com.dcg.command.Input;
import com.dcg.command.Target;
import java.util.function.BiFunction;

/** A function to select the target of the command given originating source entity and input. */
public interface TargetFunction extends BiFunction<Integer, Input, Target> {}
