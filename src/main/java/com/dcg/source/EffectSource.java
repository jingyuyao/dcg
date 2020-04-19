package com.dcg.source;

import com.dcg.command.Target;
import java.util.function.Supplier;

// TODO: change this into something with getCount() and getValue()
/**
 * An additional source of target for the command. Result will be automatically memorized by the
 * command. Instances will be injected.
 */
public interface EffectSource extends Supplier<Target> {}
