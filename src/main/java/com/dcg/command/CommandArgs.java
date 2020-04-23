package com.dcg.command;

/** A supplier to get an optional value for the command. Instances will be injected. */
public class CommandArgs {
  private final int intValue;
  private final boolean boolValue;

  public CommandArgs(int intValue, boolean boolValue) {
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
