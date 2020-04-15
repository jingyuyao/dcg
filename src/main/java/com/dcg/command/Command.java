package com.dcg.command;

import java.util.Collections;
import java.util.List;

public abstract class Command {
  private List<Integer> input = Collections.emptyList();

  public abstract void run();

  /** Returns whether this command can run with the given input. */
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
