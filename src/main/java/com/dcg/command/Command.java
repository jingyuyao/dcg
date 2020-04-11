package com.dcg.command;

import java.util.Collections;
import java.util.List;

public abstract class Command {
  private List<Integer> args = Collections.emptyList();

  public abstract void run();

  public void setArgs(List<Integer> args) {
    this.args = args;
  }

  public List<Integer> getArgs() {
    return args;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + args + " ";
  }
}
