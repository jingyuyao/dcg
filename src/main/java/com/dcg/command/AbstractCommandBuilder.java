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

  protected String getDescription(CommandData data) {
    return "";
  }

  protected boolean isClientVisible(CommandData data) {
    return false;
  }

  private class CommandImpl implements Command {
    private final int originEntity;
    private List<Integer> inputs = Collections.emptyList();

    private CommandImpl(int originEntity) {
      this.originEntity = originEntity;
    }

    @Override
    public String getName() {
      return AbstractCommandBuilder.this.getClass().getSimpleName();
    }

    @Override
    public String getDescription(CommandData data) {
      return AbstractCommandBuilder.this.getDescription(data);
    }

    @Override
    public boolean isClientVisible(CommandData data) {
      return AbstractCommandBuilder.this.isClientVisible(data);
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
    public void run(CommandData data) {
      AbstractCommandBuilder.this.run(data);
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

    @Override
    public CommandData getData() {
      Integer intArg = null;
      if (intArgSupplier != null) {
        world.inject(intArgSupplier);
        intArg = intArgSupplier.get();
      }
      Boolean boolArg = null;
      if (boolArgSupplier != null) {
        world.inject(boolArgSupplier);
        boolArg = boolArgSupplier.get();
      }
      return new CommandData(originEntity, getTargets(), intArg, boolArg);
    }

    private List<Integer> getTargets() {
      return minInputCount > 0 ? inputs : getAllowedTargets();
    }
  }
}
