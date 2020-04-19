package com.dcg.command;

import com.artemis.World;
import com.artemis.annotations.Wire;
import com.dcg.condition.TargetCondition;
import com.dcg.condition.WorldCondition;
import com.dcg.game.CoreSystem;
import com.dcg.source.CommandSource;
import com.dcg.source.SourceEntity;
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
  // Child classes should only access the provided Target instance during conditions checks or run.
  private int sourceEntity = -1;
  private CommandSource commandSource = new SourceEntity();

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

  public AbstractCommandBuilder setCommandSource(CommandSource commandSource) {
    this.commandSource = commandSource;
    return this;
  }

  protected abstract void run(Target target);

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }

  private class CommandImpl implements Command {
    private Input input = OptionalInt::empty;

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
      world.inject(commandSource);
      List<Integer> result = commandSource.apply(sourceEntity, input).get();
      // Cache result before passing to downstream so implementations don't have to cache
      // themselves.
      return () -> result;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder(AbstractCommandBuilder.this.toString());
      for (int target : getMemorizedTarget(input).get()) {
        builder.append(" ").append(coreSystem.toName(target));
      }
      return builder.toString();
    }
  }
}
