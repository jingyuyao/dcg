package com.dcg.action;

import com.artemis.ComponentMapper;
import com.dcg.command.CommandBuilder;
import com.dcg.game.CreateEntity;
import com.dcg.target.Target;
import net.mostlyoriginal.api.utils.Preconditions;

public class CreateAction extends CreateEntity {
  private final CommandBuilder builder;
  protected ComponentMapper<Action> mAction;

  public CreateAction(CommandBuilder builder) {
    super(builder.toString());
    this.builder = builder;
    addTargetConditions(
        target ->
            Preconditions.checkArgument(getOwner(target).isPresent(), "Target must have owner"));
  }

  @Override
  protected void run(Target target) {
    int actionEntity = createEntity(target);
    mAction.create(actionEntity).command = builder.build(world, getOwner(target).orElse(-1));
  }
}
