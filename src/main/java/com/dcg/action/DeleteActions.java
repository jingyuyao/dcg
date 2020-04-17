package com.dcg.action;

import com.artemis.Aspect;
import com.dcg.command.CommandBase;
import com.dcg.game.CoreSystem;

public class DeleteActions extends CommandBase {
  protected CoreSystem coreSystem;

  @Override
  protected void run() {
    coreSystem.getOwnedBy(sourceEntity, Aspect.all(Action.class)).forEach(world::delete);
  }
}
