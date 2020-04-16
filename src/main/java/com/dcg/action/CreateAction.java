package com.dcg.action;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.dcg.command.Command;
import com.dcg.ownership.Owned;

public class CreateAction extends Command {
  private final int ownerEntity;
  private final Command command;
  protected World world;
  protected ComponentMapper<Action> mAction;
  protected ComponentMapper<Owned> mOwned;

  public CreateAction(int ownerEntity, Command command) {
    this.ownerEntity = ownerEntity;
    this.command = command;
  }

  @Override
  protected void run() {
    int actionEntity = world.create();
    mAction.create(actionEntity).command = command;
    // TODO: inherit from CreateEntity and use this.owner
    mOwned.create(actionEntity).owner = ownerEntity;
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), command);
  }
}
