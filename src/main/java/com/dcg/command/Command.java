package com.dcg.command;

import java.util.Collections;
import java.util.List;

public abstract class Command {
  protected List<Integer> input = Collections.emptyList();

  /** Sets the optional user input for this command. */
  public void setInput(List<Integer> input) {
    this.input = input;
  }

  /** Returns the optional user input for this command. */
  public List<Integer> getInput() {
    return input;
  }

  /**
   * Execute the logic associated with this command. This should only be called by {@link
   * CommandExecutor}.
   */
  void execute() {
    run();
  }

  /**
   * Returns whether the preconditions this command is satisfied. This should only be called by
   * {@link CommandExecutor}.
   */
  boolean canExecute() {
    return canRun();
  }

  /** Override to provide the logic for this command. This must be repeatedly callable. */
  protected abstract void run();

  // TODO: make this return an Optional<String>, if empty = canRun, else string contains error.

  /**
   * Override to provide whether the preconditions for this command is satisfied. This must not have
   * any side effects.
   */
  protected boolean canRun() {
    return true;
  }

  @Override
  public String toString() {
    return String.format("%s%s", getClass().getSimpleName(), input);
  }
}
