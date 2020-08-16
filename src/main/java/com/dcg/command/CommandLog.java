package com.dcg.command;

/** Contains data for a single invocation of a command. */
public class CommandLog {
  private final String commandName;
  private final String description;
  private final boolean clientVisible;
  // Note: Log can be accessed at any point in the game. We should only reference "permanent"
  // entities.
  private final int currentPlayerEntity;
  private final int originCardEntity;

  CommandLog(
      String commandName,
      String description,
      boolean clientVisible,
      int currentPlayerEntity,
      int originCardEntity) {
    this.commandName = commandName;
    this.description = description;
    this.clientVisible = clientVisible;
    this.currentPlayerEntity = currentPlayerEntity;
    this.originCardEntity = originCardEntity;
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

  public int getCurrentPlayerEntity() {
    return currentPlayerEntity;
  }

  public int getOriginCardEntity() {
    return originCardEntity;
  }
}
