package com.dcg.action;

import com.artemis.ComponentMapper;
import com.dcg.command.CommandBuilder;
import com.dcg.game.CreateEntity;
import java.util.List;
import java.util.OptionalInt;
import net.mostlyoriginal.api.utils.Preconditions;

public class CreateAction extends CreateEntity {
  private final CommandBuilder builder;
  protected ComponentMapper<Action> mAction;

  public CreateAction(CommandBuilder builder) {
    this(builder.toString(), builder);
  }

  public CreateAction(String name, CommandBuilder builder) {
    super(name);
    this.builder = builder;
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, int value) {
    OptionalInt owner = getOwner(originEntity);
    // TODO: replace this with our own variant that emits GameStateException and catch it during
    // exec
    Preconditions.checkArgument(owner.isPresent());
    int actionEntity = createEntity(originEntity);
    mAction.create(actionEntity).command = builder.build(world, owner.getAsInt());
  }
}
