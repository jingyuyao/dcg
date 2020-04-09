package com.dcg.command;

public abstract class Command {

  public abstract void run();

  @Override
  public String toString() {
    return getClass().getSimpleName() + " ";
  }
}
