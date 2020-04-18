package com.dcg.action;

import com.artemis.ComponentMapper;
import com.dcg.command.CommandBuilder;
import com.dcg.command.Target;
import com.dcg.game.CreateEntity;

public class CreateAction extends CreateEntity {
  private final CommandBuilder builder;
  protected ComponentMapper<Action> mAction;

  public CreateAction(CommandBuilder builder) {
    this.builder = builder;
    addTargetConditions(target -> getOwner(target).isPresent());
  }

  @Override
  protected void run(Target target) {
    int actionEntity = createEntity(target);
    mAction.create(actionEntity).command = builder.build(world, getOwner(target).orElse(-1));
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), builder);
  }
}
