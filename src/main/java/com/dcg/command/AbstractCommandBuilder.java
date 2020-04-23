package com.dcg.command;

import com.artemis.World;
import com.artemis.annotations.Wire;
import com.dcg.condition.TriggerCondition;
import com.dcg.game.CoreSystem;
import com.dcg.target.OriginEntity;
import com.dcg.target.Target;
import com.dcg.target.TargetSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Base class for building a {@link Command}. Guarantees the generated {@link Command} instance is
 * injected by a world. By default the target source is the entity the command is attached to.
 * Commands will either target all entities specified by its {@link TargetSource} or all inputs if
 * inputs are required.
 */
public abstract class AbstractCommandBuilder implements CommandBuilder {
  @Wire protected CommandChain commandChain;
  protected World world;
  protected CoreSystem coreSystem;
  private final List<TriggerCondition> triggerConditions = new ArrayList<>();
  private TargetSource targetSource = new OriginEntity();
  // TODO: i think these should still be called input counts, since they don't restrict the use
  // allowed sources case.
  private int minTargetCount = 0;
  private int maxTargetCount = 0;
  private boolean injected = false;

  @Override
  public Command build(World world, int originEntity) {
    if (!injected) {
      world.inject(this);
      injected = true;
    }
    return this.new CommandImpl(originEntity);
  }

  public AbstractCommandBuilder setTargetSource(TargetSource targetSource) {
    this.targetSource = targetSource;
    return this;
  }

  public AbstractCommandBuilder setTargetCount(int minMaxTargetCount) {
    return setTargetCount(minMaxTargetCount, minMaxTargetCount);
  }

  /**
   * Sets the number of required inputs. Command will automatically use input rather than all
   * allowed targets when this is set.
   */
  public AbstractCommandBuilder setTargetCount(int minTargetCount, int maxTargetCount) {
    this.minTargetCount = minTargetCount;
    this.maxTargetCount = maxTargetCount;
    return this;
  }

  public AbstractCommandBuilder addTriggerConditions(TriggerCondition... triggerConditions) {
    Collections.addAll(this.triggerConditions, triggerConditions);
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
    public int getMinTargetCount() {
      return minTargetCount;
    }

    @Override
    public int getMaxTargetCount() {
      return maxTargetCount;
    }

    @Override
    public List<Integer> getAllowedTargets() {
      world.inject(targetSource);
      return targetSource.getAllowedTargets(originEntity);
    }

    @Override
    public void run() {
      List<Integer> targets = minTargetCount > 0 ? inputs : getAllowedTargets();
      // TODO: just pass in these as normal arguments
      AbstractCommandBuilder.this.run(
          new Target() {
            @Override
            public int getOrigin() {
              return originEntity;
            }

            @Override
            public List<Integer> getTargets() {
              return targets;
            }
          });
    }

    @Override
    public boolean canRun() {
      return canTrigger() && isInputValid();
    }

    @Override
    public boolean isInputValid() {
      if (inputs.size() < minTargetCount) {
        System.out.printf("Fail: Minimum %d input required\n", minTargetCount);
        return false;
      }
      if (inputs.size() > maxTargetCount) {
        System.out.printf("Fail: Maximum %d input allowed\n", maxTargetCount);
        return false;
      }
      if (!getAllowedTargets().containsAll(inputs)) {
        System.out.println("Fail: Not all inputs are allowed");
        return false;
      }
      return true;
    }

    @Override
    public boolean canTrigger() {
      for (TriggerCondition condition : triggerConditions) {
        world.inject(condition);
        if (!condition.test(originEntity)) {
          return false;
        }
      }
      return true;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder(AbstractCommandBuilder.this.toString());
      builder
          .append(" ")
          .append(coreSystem.toName(originEntity))
          .append("(")
          .append(originEntity)
          .append(")");
      if (!inputs.isEmpty() && (inputs.size() > 1 || inputs.get(0) != originEntity)) {
        builder.append(" ->");
        for (int entity : inputs) {
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
