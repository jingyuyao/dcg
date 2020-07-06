package com.dcg.command;

import java.util.List;

/** Contains the data used for a single execution of a command. */
public class CommandData {
  private final int originEntity;
  private final List<Integer> targets;
  private final int intValue;
  private final boolean boolValue;

  public CommandData(int originEntity, List<Integer> targets, int intValue, boolean boolValue) {
    this.originEntity = originEntity;
    this.targets = targets;
    this.intValue = intValue;
    this.boolValue = boolValue;
  }

  public int getInt() {
    return intValue;
  }

  public boolean getBool() {
    return boolValue;
  }
}
