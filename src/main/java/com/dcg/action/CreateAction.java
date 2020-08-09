package com.dcg.action;

import com.artemis.ComponentMapper;
import com.dcg.command.CommandBuilder;
import com.dcg.command.CommandData;
import com.dcg.game.CreateEntity;
import com.dcg.game.Owned;
import com.dcg.game.Preconditions;

public class CreateAction extends CreateEntity {
  private final CommandBuilder builder;
  protected ComponentMapper<Action> mAction;
  protected ComponentMapper<Owned> mOwned;

  private CreateAction(String name, CommandBuilder builder) {
    super(name);
    this.builder = builder;
  }

  public static CreateAction action(CommandBuilder builder) {
    // TODO: don't use getSimpleName()
    return new CreateAction(builder.getClass().getSimpleName(), builder);
  }

  public static CreateAction action(String name, CommandBuilder builder) {
    return new CreateAction(name, builder);
  }

  @Override
  protected void run(CommandData data) {
    int originEntity = data.getOriginEntity();
    Preconditions.checkGameState(originEntity != -1, "Must have origin");
    int actionEntity = createEntity();
    mOwned.create(actionEntity).owner = originEntity;
    mAction.create(actionEntity).command = builder.build(world, originEntity);
  }

  @Override
  protected String getDescription(CommandData data) {
    return String.format(
        "%s for %s", super.getDescription(data), coreSystem.toName(data.getOriginEntity()));
  }
}
