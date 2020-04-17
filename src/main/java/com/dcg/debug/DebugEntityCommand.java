package com.dcg.debug;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.action.Action;
import com.dcg.command.CommandBase;
import com.dcg.command.ExecutableCommand;
import com.dcg.game.OwnershipSystem;

public abstract class DebugEntityCommand extends CommandBase {
  protected OwnershipSystem ownershipSystem;
  protected ComponentMapper<Action> mAction;

  protected void printActions(int ownerEntity) {
    ownershipSystem.getOwnedBy(ownerEntity, Aspect.all(Action.class)).forEach(this::printAction);
  }

  private void printAction(int actionEntity) {
    Action action = mAction.get(actionEntity);
    ExecutableCommand executableCommand =
        action.command.build(world, ownershipSystem.getOwner(actionEntity));
    // Only show actions that are valid in the current world state.
    if (executableCommand.isWorldValid()) {
      System.out.printf("    - %d %s\n", actionEntity, action.command);
    }
  }
}
