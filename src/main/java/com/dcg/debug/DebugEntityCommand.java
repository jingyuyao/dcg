package com.dcg.debug;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.World;
import com.dcg.action.Action;
import com.dcg.command.Command;
import com.dcg.ownership.OwnershipSystem;

public abstract class DebugEntityCommand extends Command {
  protected World world;
  protected OwnershipSystem ownershipSystem;
  protected ComponentMapper<Action> mAction;

  protected void printActions(int ownerEntity) {
    ownershipSystem.getOwnedBy(ownerEntity, Aspect.all(Action.class)).forEach(this::printAction);
  }

  private void printAction(int actionEntity) {
    Command command = mAction.get(actionEntity).command;
    // Only show actions that are valid in the current world state.
    if (command.isWorldValid(world)) {
      System.out.printf("< *%d %s\n", actionEntity, command);
    }
  }
}
