package com.dcg.action;

import com.artemis.Aspect;
import com.artemis.World;
import com.dcg.command.Command;
import com.dcg.ownership.OwnershipSystem;

public class DeleteActions extends Command {
  protected OwnershipSystem ownershipSystem;
  protected World world;

  @Override
  protected void run() {
    ownershipSystem.getOwnedBy(owner, Aspect.all(Action.class)).forEach(world::delete);
  }
}
