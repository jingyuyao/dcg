package com.dcg.command;

import com.artemis.World;
import com.artemis.annotations.Wire;
import com.dcg.game.CoreSystem;
import com.dcg.targetsource.OriginEntity;
import com.dcg.targetsource.TargetSource;
import com.dcg.triggercondition.TriggerCondition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

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
  private Supplier<Integer> intArgSupplier;
  private Supplier<Boolean> boolArgSupplier;
  private TargetSource targetSource = new OriginEntity();
  private int minInputCount = 0;
  private int maxInputCount = 0;
  private boolean injected = false;

  @Override
  public Command build(World world, int originEntity) {
    if (!injected) {
      world.inject(this);
      injected = true;
    }
    return this.new CommandImpl(originEntity);
  }

  public AbstractCommandBuilder setIntArgSupplier(Supplier<Integer> intArgSupplier) {
    this.intArgSupplier = intArgSupplier;
    return this;
  }

  public AbstractCommandBuilder setBoolArgSupplier(Supplier<Boolean> boolArgSupplier) {
    this.boolArgSupplier = boolArgSupplier;
    return this;
  }

  public AbstractCommandBuilder setTargetSource(TargetSource targetSource) {
    this.targetSource = targetSource;
    return this;
  }

  public AbstractCommandBuilder setInputCount(int minMaxInputCount) {
    return setInputCount(minMaxInputCount, minMaxInputCount);
  }

  /**
   * Sets the number of required inputs. Command will automatically use input rather than all
   * allowed targets when this is set.
   */
  public AbstractCommandBuilder setInputCount(int minInputCount, int maxInputCount) {
    this.minInputCount = minInputCount;
    this.maxInputCount = maxInputCount;
    return this;
  }

  public AbstractCommandBuilder addTriggerConditions(TriggerCondition... triggerConditions) {
    Collections.addAll(this.triggerConditions, triggerConditions);
    return this;
  }

  protected abstract void run(CommandData data);

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }

  private class CommandImpl implements Command {
    private final int originEntity;
    private List<Integer> inputs = Collections.emptyList();
    private String snapshot;

    private CommandImpl(int originEntity) {
      this.originEntity = originEntity;
    }

    @Override
    public String getSnapshot() {
      return snapshot != null ? snapshot : toString();
    }

    @Override
    public int getOriginEntity() {
      return originEntity;
    }

    @Override
    public Command setInput(List<Integer> input) {
      this.inputs = input;
      return this;
    }

    @Override
    public int getMinInputCount() {
      return minInputCount;
    }

    @Override
    public int getMaxInputCount() {
      return maxInputCount;
    }

    @Override
    public List<Integer> getAllowedTargets() {
      world.inject(targetSource);
      return targetSource.getAllowedTargets(originEntity);
    }

    @Override
    public void run() {
      // NOTE: toString() may miss some information if the command delete entities. Thus we save
      // the snapshot before the command is run and use that for logging, etc.
      snapshot = toString();
      AbstractCommandBuilder.this.run(getData());
    }

    @Override
    public boolean canRun() {
      return canTrigger() && isInputValid();
    }

    @Override
    public boolean isInputValid() {
      if (inputs.size() < minInputCount) {
        System.out.printf("Fail: Minimum %d input required\n", minInputCount);
        return false;
      }
      if (inputs.size() > maxInputCount) {
        System.out.printf("Fail: Maximum %d input allowed\n", maxInputCount);
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
      List<Integer> allowedTargets = getAllowedTargets();
      for (TriggerCondition condition : triggerConditions) {
        world.inject(condition);
        if (!condition.test(originEntity, allowedTargets)) {
          return false;
        }
      }
      return true;
    }

    private CommandData getData() {
      int intArg = 0;
      if (intArgSupplier != null) {
        world.inject(intArgSupplier);
        intArg = intArgSupplier.get();
      }
      boolean boolArg = false;
      if (boolArgSupplier != null) {
        world.inject(boolArgSupplier);
        boolArg = boolArgSupplier.get();
      }
      return new CommandData(originEntity, getTargets(), intArg, boolArg);
    }

    private List<Integer> getTargets() {
      return minInputCount > 0 ? inputs : getAllowedTargets();
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder(coreSystem.toName(originEntity));
      builder.append(": ").append(AbstractCommandBuilder.this.toString());
      CommandData args = getData();
      if (intArgSupplier != null) {
        builder.append(" = ").append(args.getInt());
      }
      if (boolArgSupplier != null) {
        builder.append(" = ").append(args.getBool());
      }
      List<Integer> targets = getTargets();
      if (!targets.isEmpty() && (targets.size() > 1 || targets.get(0) != originEntity)) {
        builder.append(" >>");
        for (int entity : targets) {
          builder.append(" ").append(coreSystem.toName(entity));
        }
      }
      return builder.toString();
    }
  }
}
