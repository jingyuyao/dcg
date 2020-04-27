package com.dcg.action;

import com.artemis.ComponentMapper;
import com.dcg.command.CommandArgs;
import com.dcg.command.CommandBuilder;
import com.dcg.game.CreateEntity;
import com.dcg.game.Preconditions;
import java.util.List;
import java.util.OptionalInt;

public class CreateAction extends CreateEntity {
  private final CommandBuilder builder;
  protected ComponentMapper<Action> mAction;

  public CreateAction(CommandBuilder builder) {
    super(builder.toString());
    this.builder = builder;
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    OptionalInt owner = getOwner(originEntity);
    Preconditions.checkGameState(owner.isPresent(), "Must have owner for origin %d", originEntity);
    int actionEntity = createEntity(originEntity);
    mAction.create(actionEntity).command = builder.build(world, owner.getAsInt());
  }
}
