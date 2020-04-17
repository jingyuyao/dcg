package com.dcg.effect;

import com.dcg.command.AbstractCommandBuilder;
import java.util.stream.IntStream;

/** A specialized command with structured trigger logic. */
public abstract class AbstractEffectBuilder extends AbstractCommandBuilder {
  @Override
  protected boolean isInputValid() {
    return isSourceEntityValid() || isTargetEntitiesValid();
  }

  protected IntStream getTargetEntities() {
    return isSourceEntityValid()
        ? IntStream.of(sourceEntity)
        : input.stream().mapToInt(Integer::intValue);
  }

  protected abstract boolean isTargetEntityValid(int targetEntity);

  protected int getMaxTargetCount() {
    return 1;
  }

  private boolean isSourceEntityValid() {
    return isTargetEntityValid(sourceEntity);
  }

  private boolean isTargetEntitiesValid() {
    return input.size() > 0
        && input.size() <= getMaxTargetCount()
        && input.stream().allMatch(this::isTargetEntityValid);
  }
}
