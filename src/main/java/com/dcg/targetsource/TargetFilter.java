package com.dcg.targetsource;

import java.util.function.BiFunction;
import java.util.stream.Stream;

// TODO: is there no way to make this into <Integer, Integer, Boolean>?
public interface TargetFilter extends BiFunction<Integer, Stream<Integer>, Stream<Integer>> {}
