package com.dcg.command;

import com.artemis.World;
import java.util.Collections;
import java.util.List;

/**
 * Base class for a command. Guarantees the instance is injected by a world and has owner set upon
 * execution as long as only the public interfaces are used.
 */
public abstract class CommandBase implements Command {
  protected World world;
  protected int sourceEntity = -1;
  protected List<Integer> input = Collections.emptyList();
  private boolean injected = false;

  @Override
  public ExecutableCommand build(World world, int sourceEntity) {
    if (!injected) {
      world.inject(this);
      injected = true;
    }
    this.sourceEntity = sourceEntity;
    return this.new ExecutableCommandImpl();
  }

  protected abstract void run();

  protected boolean isInputValid() {
    return true;
  }

  protected boolean isWorldValid() {
    return true;
  }

  @Override
  public String toString() {
    return String.format("%s(source:*%d)%s", getClass().getSimpleName(), sourceEntity, input);
  }

  private class ExecutableCommandImpl implements ExecutableCommand {
    @Override
    public ExecutableCommand setInput(List<Integer> input) {
      CommandBase.this.input = input;
      return this;
    }

    @Override
    public void run() {
      CommandBase.this.run();
    }

    @Override
    public boolean canRun() {
      return isInputValid() && isWorldValid();
    }

    @Override
    public boolean isInputValid() {
      return CommandBase.this.isInputValid();
    }

    @Override
    public boolean isWorldValid() {
      return CommandBase.this.isWorldValid();
    }

    @Override
    public String toString() {
      return CommandBase.this.toString();
    }
  }
}
