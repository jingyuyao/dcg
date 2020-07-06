package com.dcg.command;

import java.util.List;

/** Contains the data used for a single execution of a command. */
public class CommandData {
  // The command name is the simple class name of its builder.
  private final String name;
  private final int originEntity;
  private final List<Integer> targets;
  private final int intValue;
  private final boolean boolValue;

  public CommandData(
      String name, int originEntity, List<Integer> targets, int intValue, boolean boolValue) {
    this.name = name;
    this.originEntity = originEntity;
    this.targets = targets;
    this.intValue = intValue;
    this.boolValue = boolValue;
  }

  public String getName() {
    return name;
  }

  public int getOriginEntity() {
    return originEntity;
  }

  public List<Integer> getTargets() {
    return targets;
  }

  public int getInt() {
    return intValue;
  }

  public boolean getBool() {
    return boolValue;
  }
}
