package com.dcg.action;

import com.artemis.ComponentMapper;
import com.dcg.command.CommandArgs;
import com.dcg.command.CommandBuilder;
import com.dcg.game.CreateEntity;
import com.dcg.game.Owned;
import com.dcg.game.Preconditions;
import java.util.List;

public class CreateAction extends CreateEntity {
  private final CommandBuilder builder;
  protected ComponentMapper<Action> mAction;
  protected ComponentMapper<Owned> mOwned;

  private CreateAction(CommandBuilder builder) {
    super(builder.toString());
    this.builder = builder;
  }

  public static CreateAction action(CommandBuilder builder) {
    return new CreateAction(builder);
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    Preconditions.checkGameState(originEntity != -1, "Must have owner for origin %d", originEntity);
    int actionEntity = createEntity();
    mOwned.create(actionEntity).owner = originEntity;
    mAction.create(actionEntity).command = builder.build(world, originEntity);
  }
}
