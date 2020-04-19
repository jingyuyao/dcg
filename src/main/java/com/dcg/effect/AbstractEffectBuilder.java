package com.dcg.effect;

import com.dcg.command.AbstractCommandBuilder;

/** A command builder with an additional source for targets. */
public abstract class AbstractEffectBuilder extends AbstractCommandBuilder {
  private EffectValueSupplier effectValueSupplier = () -> 1;

  public AbstractEffectBuilder setEffectValueSupplier(EffectValueSupplier effectValueSupplier) {
    this.effectValueSupplier = effectValueSupplier;
    return this;
  }

  protected int getEffectValue() {
    world.inject(effectValueSupplier);
    return effectValueSupplier.get();
  }
}
