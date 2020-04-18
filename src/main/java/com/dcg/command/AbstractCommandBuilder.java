package com.dcg.command;

import com.artemis.World;
import com.artemis.annotations.Wire;
import com.dcg.condition.TargetCondition;
import com.dcg.condition.WorldCondition;
import com.dcg.game.CoreSystem;
import com.dcg.targetsource.SourceEntity;
import com.dcg.targetsource.TargetSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Base class for a command. Guarantees the instance is injected by a world and has source entity
 * set upon execution as long as only the public interfaces are used.
 */
public abstract class AbstractCommandBuilder implements CommandBuilder {
  private final List<WorldCondition> worldConditions = new ArrayList<>();
  private final List<TargetCondition> targetConditions = new ArrayList<>();
  @Wire protected CommandChain commandChain;
  protected World world;
  protected CoreSystem coreSystem;
  protected int sourceEntity = -1;
  private boolean injected = false;
  // TODO: allow multiple for composition
  private TargetSource targetSource = new SourceEntity();

  @Override
  public Command build(World world, int sourceEntity) {
    if (!injected) {
      world.inject(this);
      injected = true;
    }
    this.sourceEntity = sourceEntity;
    return this.new CommandImpl();
  }

  public AbstractCommandBuilder addWorldConditions(WorldCondition... worldConditions) {
    Collections.addAll(this.worldConditions, worldConditions);
    return this;
  }

  public AbstractCommandBuilder addTargetConditions(TargetCondition... targetConditions) {
    Collections.addAll(this.targetConditions, targetConditions);
    return this;
  }

  public AbstractCommandBuilder setTargetSource(TargetSource targetSource) {
    this.targetSource = targetSource;
    return this;
  }

  protected abstract void run(List<Integer> input);

  // TODO: type both input and target
  protected List<Integer> getTargetEntities(List<Integer> input) {
    world.inject(targetSource);
    return targetSource.get(sourceEntity, input);
  }

  private boolean isWorldValid() {
    return worldConditions.stream()
        .peek(world::inject)
        .allMatch(worldCondition -> worldCondition.test(coreSystem));
  }

  private boolean isInputValid(List<Integer> input) {
    List<Integer> targetEntities = getTargetEntities(input);
    return targetConditions.stream()
        .peek(world::inject)
        .allMatch(targetCondition -> targetCondition.test(targetEntities));
  }

  @Override
  public String toString() {
    return String.format("%s(source:*%d)", getClass().getSimpleName(), sourceEntity);
  }

  private class CommandImpl implements Command {
    private List<Integer> input = Collections.emptyList();

    @Override
    public Command setInput(List<Integer> input) {
      this.input = input;
      return this;
    }

    @Override
    public void run() {
      AbstractCommandBuilder.this.run(input);
    }

    @Override
    public boolean canRun() {
      return isWorldValid() && isInputValid();
    }

    @Override
    public boolean isInputValid() {
      return AbstractCommandBuilder.this.isInputValid(input);
    }

    @Override
    public boolean isWorldValid() {
      return AbstractCommandBuilder.this.isWorldValid();
    }

    @Override
    public String toString() {
      return String.format("%s%s", AbstractCommandBuilder.this.toString(), input);
    }
  }
}
