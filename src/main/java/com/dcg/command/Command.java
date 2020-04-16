package com.dcg.command;

import com.artemis.World;
import java.util.Collections;
import java.util.List;

public abstract class Command {
  protected List<Integer> input = Collections.emptyList();
  private boolean injected = false;

  /** Sets the optional user input for this command. */
  public final void setInput(List<Integer> input) {
    this.input = input;
  }

  /** Execute the logic associated with this command. */
  public final void run(World world) {
    injectFrom(world);
    run();
  }

  // TODO: split world state validation from input validation so invalid world state commands
  // can be hidden from players.
  /** Returns whether all the preconditions for this command are satisfied. */
  public final boolean canRun(World world) {
    injectFrom(world);
    return isInputValid() && isWorldValid();
  }

  /** Returns whether the current input is valid for the command. */
  public final boolean isInputValid(World world) {
    injectFrom(world);
    return isInputValid();
  }

  /** Returns whether the command can be run in the current world state. */
  public final boolean isWorldValid(World world) {
    injectFrom(world);
    return isWorldValid();
  }

  /**
   * Override to provide the logic for this command. This must be repeatedly callable. Do not call
   * this from anywhere outside of this class. Use {@link #run(World)} instead.
   */
  protected abstract void run();

  /**
   * Override to provide whether the current input is valid for this command is satisfied. No side
   * effects allowed. Do not call this from anywhere outside of this class. Use {@link
   * #isInputValid(World)} instead.
   */
  protected boolean isInputValid() {
    return true;
  }

  /**
   * Override to provide whether the command can be run in the current world state. No side effects
   * allowed. Do not call this from anywhere outside of this class. Use {@link #isWorldValid(World)}
   * instead.
   */
  protected boolean isWorldValid() {
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
