package com.dcg.command;

import java.util.List;
import java.util.function.Supplier;

// TODO: change this into something with getFrom() and getTo()
/** Marker type for command targets. */
public interface Target extends Supplier<List<Integer>> {}
