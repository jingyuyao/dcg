package com.dcg.debug;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.action.Action;
import com.dcg.command.Command;
import com.dcg.ownership.OwnershipSystem;

public abstract class DebugEntityCommand extends Command {
  protected OwnershipSystem ownershipSystem;
  protected ComponentMapper<Action> mAction;

  protected void printActions(int ownerEntity) {
    ownershipSystem
        .getOwnedBy(ownerEntity, Aspect.all(Action.class))
        .forEach(
            actionEntity ->
                System.out.printf(
                    "        <*%d> %s\n", actionEntity, mAction.get(actionEntity).command));
  }
}
