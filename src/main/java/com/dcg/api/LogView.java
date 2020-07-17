package com.dcg.api;

import java.util.List;

public class LogView {
  public final String commandName;
  public final String originName;
  public final String ownerName;
  public final List<String> targets;
  public final Integer intValue;
  public final Boolean boolValue;

  public LogView(
      String commandName,
      String originName,
      String ownerName,
      List<String> targets,
      Integer intValue,
      Boolean boolValue) {
    this.commandName = commandName;
    this.originName = originName;
    this.ownerName = ownerName;
    this.targets = targets;
    this.intValue = intValue;
    this.boolValue = boolValue;
  }
}
