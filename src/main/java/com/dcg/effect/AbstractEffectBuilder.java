package com.dcg.effect;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/** A specialized command with structured trigger logic against entities. */
public abstract class AbstractEffectBuilder<T extends Component> extends AbstractCommandBuilder {
  @Override
  protected boolean isInputValid() {
    return isSourceEntityValid() || isTargetEntitiesValid();
  }

  protected Stream<T> getTargetComponents() {
    ComponentMapper<T> componentMapper = getComponentMapper();
    return getTargetEntities().mapToObj(componentMapper::get);
  }

  protected IntStream getTargetEntities() {
    return isSourceEntityValid()
        ? IntStream.of(transformTargetEntity(sourceEntity))
        : input.stream().mapToInt(Integer::intValue);
  }

  /** Provide the {@link ComponentMapper} used to validate and transform target entities. */
  protected abstract ComponentMapper<T> getComponentMapper();

  /** Override me to provide the max target count for this effect. Defaults to 1. */
  protected int getMaxTargetCount() {
    return 1;
  }

  /** Override me to transform the target entity into another entity. */
  protected int transformTargetEntity(int targetEntity) {
    return targetEntity;
  }

  private boolean isSourceEntityValid() {
    return isTargetEntityValid(sourceEntity);
  }

  private boolean isTargetEntitiesValid() {
    return input.size() > 0
        && input.size() <= getMaxTargetCount()
        && input.stream().allMatch(this::isTargetEntityValid);
  }

  private boolean isTargetEntityValid(int targetEntity) {
    return getComponentMapper().has(transformTargetEntity(targetEntity));
  }
}
