package com.dcg.command;

import java.util.Collections;
import java.util.List;

public abstract class Command {
  private List<Integer> targetEntities = Collections.emptyList();

  public abstract void run();

  public void setTargetEntities(List<Integer> targetEntities) {
    this.targetEntities = targetEntities;
  }

  public List<Integer> getTargetEntities() {
    return targetEntities;
  }

  @Override
  public String toString() {
    return String.format("%s%s", getClass().getSimpleName(), targetEntities);
  }
}
