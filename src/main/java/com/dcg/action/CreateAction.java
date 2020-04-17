package com.dcg.action;

import com.artemis.ComponentMapper;
import com.dcg.command.CommandBuilder;
import com.dcg.game.CreateEntity;

public class CreateAction extends CreateEntity {
  private final CommandBuilder builder;
  protected ComponentMapper<Action> mAction;

  public CreateAction(CommandBuilder builder) {
    this.builder = builder;
  }

  @Override
  protected void run() {
    int actionEntity = createEntity();
    mAction.create(actionEntity).command = builder.build(world, sourceEntity);
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), builder);
  }
}
