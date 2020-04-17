package com.dcg.debug;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.action.Action;
import com.dcg.command.Command;
import com.dcg.command.CommandBase;
import com.dcg.game.CoreSystem;

public abstract class DebugEntityCommand extends CommandBase {
  protected CoreSystem coreSystem;
  protected ComponentMapper<Action> mAction;

  protected void printActions(int ownerEntity) {
    coreSystem.getOwnedBy(ownerEntity, Aspect.all(Action.class)).forEach(this::printAction);
  }

  private void printAction(int actionEntity) {
    Action action = mAction.get(actionEntity);
    Command command = action.command.build(world, coreSystem.getOwner(actionEntity));
    // Only show actions that are valid in the current world state.
    if (command.isWorldValid()) {
      System.out.printf("    - %d %s\n", actionEntity, action.command);
    }
  }
}
