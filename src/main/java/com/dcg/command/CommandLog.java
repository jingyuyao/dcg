package com.dcg.command;

/** Wraps a single instance of {@link CommandData} with additional logging information. */
public class CommandLog {
  private final CommandData data;
  private final String commandName;
  private final String description;
  private final boolean clientVisible;

  public CommandLog(
      CommandData data, String commandName, String description, boolean clientVisible) {
    this.data = data;
    this.commandName = commandName;
    this.description = description;
    this.clientVisible = clientVisible;
  }

  public CommandData getData() {
    return data;
  }

  public String getCommandName() {
    return commandName;
  }

  public String getDescription() {
    return description;
  }

  public boolean isClientVisible() {
    return clientVisible;
  }
}
