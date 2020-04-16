package com.dcg.command;

import com.artemis.World;
import java.util.Collections;
import java.util.List;

public abstract class Command {
  protected List<Integer> input = Collections.emptyList();
  private boolean injected = false;

  /** Sets the optional user input for this command. */
  public void setInput(List<Integer> input) {
    this.input = input;
  }

  /** Execute the logic associated with this command. */
  public void run(World world) {
    injectFrom(world);
    run();
  }

  /** Returns whether the preconditions this command is satisfied. */
  public boolean canRun(World world) {
    injectFrom(world);
    return canRun();
  }

  /** Override to provide the logic for this command. This must be repeatedly callable. */
  protected abstract void run();

  /**
   * Override to provide whether the preconditions for this command is satisfied. No side effects
   * allowed.
   */
  protected boolean canRun() {
    return true;
  }

  private void injectFrom(World world) {
    if (!injected) {
      world.inject(this);
      injected = true;
    }
  }

  @Override
  public String toString() {
    return String.format("%s%s", getClass().getSimpleName(), input);
  }
}
