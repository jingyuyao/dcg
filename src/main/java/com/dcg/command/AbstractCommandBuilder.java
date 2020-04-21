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
import net.mostlyoriginal.api.utils.Preconditions;

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

  public AbstractCommandBuilder() {
    // Every command always require at least one target, even if that target is the origin entity.
    // This will automatically prevent commands that requires input from running when its world
    // condition is met.
    addTargetConditions(
        target -> Preconditions.checkArgument(!target.getTargets().isEmpty(), "Input required"));
  }

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
    private List<Integer> inputs = Collections.emptyList();

    private CommandImpl(int originEntity) {
      this.originEntity = originEntity;
    }

    @Override
    public Command setInputs(List<Integer> inputs) {
      this.inputs = inputs;
      return this;
    }

    @Override
    public void run() {
      AbstractCommandBuilder.this.run(getMemorizedTarget(inputs));
    }

    @Override
    public boolean canRun() {
      return isWorldValid() && isInputValid();
    }

    @Override
    public boolean isInputValid() {
      Target target = getMemorizedTarget(inputs);
      for (TargetCondition condition : targetConditions) {
        world.inject(condition);
        try {
          condition.accept(target);
        } catch (Exception e) {
          System.out.printf("Precondition failed for %s: %s\n", this, e.getMessage());
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

    private Target getMemorizedTarget(List<Integer> inputs) {
      world.inject(targetFunction);
      return targetFunction.apply(originEntity, inputs);
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder(AbstractCommandBuilder.this.toString());
      Target target = getMemorizedTarget(inputs);
      builder
          .append(" ")
          .append(coreSystem.toName(target.getOrigin()))
          .append("(")
          .append(target.getOrigin())
          .append(")");
      List<Integer> to = target.getTargets();
      if (!to.isEmpty() && (to.size() > 1 || to.get(0) != target.getOrigin())) {
        builder.append(" ->");
        for (int entity : to) {
          builder
              .append(" ")
              .append(coreSystem.toName(entity))
              .append("(")
              .append(entity)
              .append(")");
        }
      }
      return builder.toString();
    }
  }
}
