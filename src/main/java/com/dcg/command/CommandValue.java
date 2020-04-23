package com.dcg.command;

import java.util.function.Supplier;

/** A supplier to get an optional value for the command. Instances will be injected. */
public interface CommandValue extends Supplier<Integer> {}
