package com.dcg.command;

import java.util.OptionalInt;
import java.util.function.Supplier;

/** Marker type for unvalidated user input. */
public interface Input extends Supplier<OptionalInt> {}
