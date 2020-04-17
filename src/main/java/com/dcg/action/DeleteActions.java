package com.dcg.action;

import com.artemis.Aspect;
import com.dcg.command.CommandBase;
import com.dcg.ownership.OwnershipSystem;

public class DeleteActions extends CommandBase {
  protected OwnershipSystem ownershipSystem;

  @Override
  protected void run() {
    ownershipSystem.getOwnedBy(sourceEntity, Aspect.all(Action.class)).forEach(world::delete);
  }
}
