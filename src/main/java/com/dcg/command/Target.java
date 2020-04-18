package com.dcg.command;

import java.util.List;
import java.util.function.Supplier;

/** Marker type for command targets. */
public interface Target extends Supplier<List<Integer>> {}
