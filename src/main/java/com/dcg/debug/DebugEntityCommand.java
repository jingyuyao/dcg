package com.dcg.debug;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.action.Action;
import com.dcg.command.AbstractCommandBuilder;

public abstract class DebugEntityCommand extends AbstractCommandBuilder {
  protected ComponentMapper<Action> mAction;

  protected void printActions(int ownerEntity) {
    coreSystem.getChildren(ownerEntity, Aspect.all(Action.class)).forEach(this::printAction);
  }

  private void printAction(int actionEntity) {
    Action action = mAction.get(actionEntity);
    System.out.printf("    - %d %s\n", actionEntity, action.command);
  }
}
