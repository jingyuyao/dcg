package com.dcg.command;

import java.util.List;

/** Contains the data used for a single execution of a command. */
public class CommandData {
  private final int originEntity;
  private final List<Integer> targets;
  private final Integer intValue;
  private final Boolean boolValue;

  public CommandData(int originEntity, List<Integer> targets, Integer intValue, Boolean boolValue) {
    this.originEntity = originEntity;
    this.targets = targets;
    this.intValue = intValue;
    this.boolValue = boolValue;
  }

  public int getOriginEntity() {
    return originEntity;
  }

  public List<Integer> getTargets() {
    return targets;
  }

  public int getInt() {
    return intValue == null ? 0 : intValue;
  }

  public boolean getBool() {
    return boolValue != null && boolValue;
  }
}
