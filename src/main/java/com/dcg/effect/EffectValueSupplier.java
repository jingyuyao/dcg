package com.dcg.effect;

import java.util.function.Supplier;

/** A supplier to get the value for the effect. Instances will be injected. */
public interface EffectValueSupplier extends Supplier<Integer> {}
