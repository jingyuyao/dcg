package com.dcg.command;

import java.util.Collections;
import java.util.List;

public abstract class Command {
  private List<Integer> input = Collections.emptyList();

  // TODO: how to ensure a command is always injected? a system that runs commands? a factory?
  // make world a required constructor?
  /**
   * Run the logic associated with this command. Commands must be repeatedly executable. The command
   * instance must be injected before this can be called.
   */
  public abstract void run();

  /**
   * Returns whether this command can run. The command instance must be injected before this can be
   * called.
   */
  public boolean canRun() {
    return true;
  }

  public void setInput(List<Integer> input) {
    this.input = input;
  }

  public List<Integer> getInput() {
    return input;
  }

  @Override
  public String toString() {
    return String.format("%s%s", getClass().getSimpleName(), input);
  }
}
