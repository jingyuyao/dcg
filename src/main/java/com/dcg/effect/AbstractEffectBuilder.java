package com.dcg.effect;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.targetsource.SourceEntity;
import com.dcg.targetsource.TargetSource;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/** A specialized command with structured trigger logic against entities. */
public abstract class AbstractEffectBuilder<T extends Component> extends AbstractCommandBuilder {
  private final List<BooleanSupplier> conditions = new ArrayList<>();
  private TargetSource targetSource = new SourceEntity();

  public AbstractEffectBuilder<T> setTargetSource(TargetSource targetSource) {
    this.targetSource = targetSource;
    return this;
  }

  /**
   * Add a generic condition to be checked before execution. Intended to be used in conjunction with
   * the onCondition effect. Conditions are injected before execution.
   */
  public AbstractEffectBuilder<T> addCondition(BooleanSupplier condition) {
    conditions.add(condition);
    return this;
  }

  @Override
  protected boolean isInputValid(List<Integer> input) {
    List<Integer> targetEntities = getTargetEntitiesInternal(input);
    return targetEntities.size() > 0
        && targetEntities.size() <= getMaxTargetCount()
        && targetEntities.stream().allMatch(this::isTargetEntityValid);
  }

  @Override
  protected boolean isWorldValid() {
    return conditions.isEmpty()
        || conditions.stream().peek(world::inject).allMatch(BooleanSupplier::getAsBoolean);
  }

  protected Stream<T> getTargetComponents(List<Integer> input) {
    ComponentMapper<T> componentMapper = getComponentMapper();
    return getTargetEntities(input).mapToObj(componentMapper::get);
  }

  protected IntStream getTargetEntities(List<Integer> input) {
    return getTargetEntitiesInternal(input).stream().mapToInt(Integer::intValue);
  }

  /** Provide the {@link ComponentMapper} used to validate and transform target entities. */
  protected abstract ComponentMapper<T> getComponentMapper();

  /** Override me to provide the max target count for this effect. Defaults to 1. */
  protected int getMaxTargetCount() {
    return 1;
  }

  private List<Integer> getTargetEntitiesInternal(List<Integer> input) {
    world.inject(targetSource);
    return targetSource.get(sourceEntity, input);
  }

  private boolean isTargetEntityValid(int targetEntity) {
    return getComponentMapper().has(targetEntity);
  }
}
