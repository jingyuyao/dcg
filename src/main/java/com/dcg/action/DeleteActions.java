package com.dcg.action;

import com.artemis.Aspect;
import com.artemis.World;
import com.dcg.command.Command;
import com.dcg.ownership.OwnershipSystem;

public class DeleteActions extends Command {
  private final int ownerEntity;
  protected OwnershipSystem ownershipSystem;
  protected World world;

  public DeleteActions(int ownerEntity) {
    this.ownerEntity = ownerEntity;
  }

  @Override
  public void run() {
    ownershipSystem.getOwnedBy(ownerEntity, Aspect.all(Action.class)).forEach(world::delete);
  }

  @Override
  public String toString() {
    return String.format("%s *%d", super.toString(), ownerEntity);
  }
}
