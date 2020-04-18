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
 * Base class for {@link CommandBuilder}. Guarantees the generated {@link Command} instance is
 * injected by a world.
 */
public abstract class AbstractCommandBuilder implements CommandBuilder {
  @Wire protected CommandChain commandChain;
  protected World world;
  protected CoreSystem coreSystem;
  private final List<WorldCondition> worldConditions = new ArrayList<>();
  private final List<TargetCondition> targetConditions = new ArrayList<>();
  private boolean injected = false;
  // Child classes should only access the provided Target instance during conditions checks or run.
  private int sourceEntity = -1;
  // TODO: allow multiple for composition
  private TargetSource targetSource = new SourceEntity();

  public AbstractCommandBuilder() {
    addTargetConditions(target -> target.get().size() > 0);
  }

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

  protected abstract void run(Target target);

  @Override
  public String toString() {
    return String.format("%s(source:*%d)", getClass().getSimpleName(), sourceEntity);
  }

  private class CommandImpl implements Command {
    private Input input = Collections::emptyList;

    @Override
    public Command setInput(Input input) {
      this.input = input;
      return this;
    }

    @Override
    public void run() {
      AbstractCommandBuilder.this.run(getMemorizedTarget(input));
    }

    @Override
    public boolean canRun() {
      return isWorldValid() && isInputValid();
    }

    @Override
    public boolean isInputValid() {
      Target target = getMemorizedTarget(input);
      for (int i = 0; i < targetConditions.size(); i++) {
        TargetCondition condition = targetConditions.get(i);
        world.inject(condition);
        if (!condition.test(target)) {
          System.out.printf("    %s target condition #%d failed\n", this.toString(), i);
          return false;
        }
      }
      return true;
    }

    @Override
    public boolean isWorldValid() {
      for (int i = 0; i < worldConditions.size(); i++) {
        WorldCondition condition = worldConditions.get(i);
        world.inject(condition);
        if (!condition.test(coreSystem)) {
          System.out.printf("    %s world condition #%d failed\n", this.toString(), i);
          return false;
        }
      }
      return true;
    }

    private Target getMemorizedTarget(Input input) {
      world.inject(targetSource);
      List<Integer> result = targetSource.apply(sourceEntity, input).get();
      // Cache result before passing to downstream so implementations don't have to cache
      // themselves.
      return () -> result;
    }

    @Override
    public String toString() {
      return String.format("%s%s", AbstractCommandBuilder.this.toString(), input.get());
    }
  }
}
