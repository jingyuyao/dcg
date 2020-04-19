package com.dcg.target;

import java.util.List;
import java.util.function.BiFunction;

/** A function to select the target of the command given originating source entity and input. */
public interface TargetFunction extends BiFunction<Integer, List<Integer>, Target> {}
