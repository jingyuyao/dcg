package com.dcg.command;

import java.util.List;
import java.util.Optional;

/** Contains the data used for a single execution of a command. */
public class CommandData {
  // The command name is the simple class name of its builder.
  private final String name;
  private final int originEntity;
  private final List<Integer> targets;
  private final Integer intValue;
  private final Boolean boolValue;

  public CommandData(
      String name, int originEntity, List<Integer> targets, Integer intValue, Boolean boolValue) {
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

  public Optional<Integer> getIntOptional() {
    return Optional.ofNullable(intValue);
  }

  public int getInt() {
    return intValue == null ? 0 : intValue;
  }

  public boolean getBool() {
    return boolValue == null ? false : boolValue;
  }

  public Optional<Boolean> getBoolOptional() {
    return Optional.ofNullable(boolValue);
  }
}
