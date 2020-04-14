package com.dcg.command;

import java.util.Collections;
import java.util.List;

public abstract class Command {
  private List<Integer> targetEntities = Collections.emptyList();

  public abstract void run();

  // TODO: what about making this isPreconditionMet? so we can constantly check the commands in
  // turn and auto execute when their conditions are met?
  public boolean isInputRequired() {
    return false;
  }

  public void setInputs(List<Integer> targetEntities) {
    this.targetEntities = targetEntities;
  }

  public List<Integer> getInputs() {
    return targetEntities;
  }

  @Override
  public String toString() {
    return String.format("%s%s", getClass().getSimpleName(), targetEntities);
  }
}
