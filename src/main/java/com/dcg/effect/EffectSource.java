package com.dcg.effect;

import com.dcg.command.Target;
import java.util.function.Supplier;

/**
 * An additional source of target for the command. Result will be automatically memorized by the
 * command. Instances will be injected.
 */
public interface EffectSource extends Supplier<Target> {}
