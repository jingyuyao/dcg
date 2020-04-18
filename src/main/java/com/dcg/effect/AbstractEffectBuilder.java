package com.dcg.effect;

import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.Target;
import com.dcg.source.EffectSource;
import java.util.Collections;
import java.util.List;

/** A command builder with an additional source for targets. */
public abstract class AbstractEffectBuilder extends AbstractCommandBuilder {
  private EffectSource effectSource = () -> Collections::emptyList;

  public AbstractEffectBuilder setEffectSource(EffectSource effectSource) {
    this.effectSource = effectSource;
    return this;
  }

  protected Target getEffectSource() {
    world.inject(effectSource);
    List<Integer> result = effectSource.get().get();
    return () -> result;
  }
}
