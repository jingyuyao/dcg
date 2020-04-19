package com.dcg.command;

import com.artemis.World;
import com.artemis.annotations.Wire;
import com.dcg.condition.TargetCondition;
import com.dcg.condition.WorldCondition;
import com.dcg.game.CoreSystem;
import com.dcg.target.OriginEntity;
import com.dcg.target.Target;
import com.dcg.target.TargetFunction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.OptionalInt;

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
  private TargetFunction targetFunction = new OriginEntity();

  @Override
  public Command build(World world, int originEntity) {
    if (!injected) {
      world.inject(this);
      injected = true;
    }
    return this.new CommandImpl(originEntity);
  }

  public AbstractCommandBuilder addWorldConditions(WorldCondition... worldConditions) {
    Collections.addAll(this.worldConditions, worldConditions);
    return this;
  }

  public AbstractCommandBuilder addTargetConditions(TargetCondition... targetConditions) {
    Collections.addAll(this.targetConditions, targetConditions);
    return this;
  }

  public AbstractCommandBuilder setTargetFunction(TargetFunction targetFunction) {
    this.targetFunction = targetFunction;
    return this;
  }

  protected abstract void run(Target target);

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }

  private class CommandImpl implements Command {
    private final int originEntity;
    private Input input = OptionalInt::empty;

    private CommandImpl(int originEntity) {
      this.originEntity = originEntity;
    }

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
      for (TargetCondition condition : targetConditions) {
        world.inject(condition);
        if (!condition.test(target)) {
          return false;
        }
      }
      return true;
    }

    @Override
    public boolean isWorldValid() {
      for (WorldCondition condition : worldConditions) {
        world.inject(condition);
        if (!condition.test(coreSystem)) {
          return false;
        }
      }
      return true;
    }

    private Target getMemorizedTarget(Input input) {
      world.inject(targetFunction);
      return targetFunction.apply(originEntity, input);
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder(AbstractCommandBuilder.this.toString());
      Target target = getMemorizedTarget(input);
      builder.append(" ").append(coreSystem.toName(target.getOrigin()));
      List<Integer> to = target.getTargets();
      if (!to.isEmpty() && (to.size() > 1 || to.get(0) != target.getOrigin())) {
        builder.append(" ->");
        for (int entity : to) {
          builder.append(" ").append(coreSystem.toName(entity));
        }
      }
      return builder.toString();
    }
  }
}
