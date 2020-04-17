package com.dcg.action;

import com.artemis.ComponentMapper;
import com.dcg.command.Command;
import com.dcg.game.CreateEntity;

public class CreateAction extends CreateEntity {
  private final Command command;
  protected ComponentMapper<Action> mAction;

  public CreateAction(Command command) {
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
