package com.dcg.command;

import java.util.List;
import java.util.function.Supplier;

/** Marker type for unvalidated user input. */
public interface Input extends Supplier<List<Integer>> {}
