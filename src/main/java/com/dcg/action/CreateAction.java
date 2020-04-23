package com.dcg.action;

import com.artemis.ComponentMapper;
import com.dcg.command.CommandBuilder;
import com.dcg.game.CreateEntity;
import com.dcg.target.Target;
import java.util.OptionalInt;
import net.mostlyoriginal.api.utils.Preconditions;

public class CreateAction extends CreateEntity {
  private final CommandBuilder builder;
  protected ComponentMapper<Action> mAction;

  public CreateAction(CommandBuilder builder) {
    // TODO: don use toString()
    super(builder.toString());
    this.builder = builder;
  }

  @Override
  protected void run(Target target) {
    OptionalInt owner = getOwner(target);
    // TODO: replace this with our own variant that emits GameStateException and catch it during
    // exec
    Preconditions.checkArgument(owner.isPresent());
    int actionEntity = createEntity(target);
    mAction.create(actionEntity).command = builder.build(world, getOwner(target).orElse(-1));
  }
}
