package com.dcg.action;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.dcg.game.Active;

@All({Active.class, Action.class})
public class ActionSystem extends IteratingSystem {
  protected ComponentMapper<Action> mAction;

  @Override
  protected void process(int actionEntity) {
    Action action = mAction.get(actionEntity);
    action.canTrigger = action.command.canTrigger();
    action.minInputCount = action.command.getMinInputCount();
    action.maxInputCount = action.command.getMaxInputCount();
    action.allowedTargets = action.command.getAllowedTargets();
  }
}
