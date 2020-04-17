package com.dcg.action;

import com.artemis.ComponentMapper;
import com.dcg.command.CommandBuilder;
import com.dcg.game.CreateEntity;

public class CreateAction extends CreateEntity {
  private final CommandBuilder command;
  protected ComponentMapper<Action> mAction;

  public CreateAction(CommandBuilder command) {
    this.command = command;
  }

  @Override
  protected void run() {
    int actionEntity = createEntity();
    mAction.create(actionEntity).command = command;
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), command);
  }
}
