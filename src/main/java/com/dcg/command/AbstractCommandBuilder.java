package com.dcg.command;

import com.artemis.World;
import com.artemis.annotations.Wire;
import com.dcg.game.CoreSystem;
import java.util.Collections;
import java.util.List;

/**
 * Base class for a command. Guarantees the instance is injected by a world and has owner set upon
 * execution as long as only the public interfaces are used.
 */
public abstract class AbstractCommandBuilder implements CommandBuilder {
  @Wire protected CommandChain commandChain;
  protected World world;
  protected CoreSystem coreSystem;
  protected int sourceEntity = -1;
  private boolean injected = false;

  @Override
  public Command build(World world, int sourceEntity) {
    if (!injected) {
      world.inject(this);
      injected = true;
    }
    this.sourceEntity = sourceEntity;
    return this.new CommandImpl();
  }

  protected abstract void run(List<Integer> input);

  protected boolean isInputValid(List<Integer> input) {
    return true;
  }

  protected boolean isWorldValid() {
    return true;
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
      return isInputValid() && isWorldValid();
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
