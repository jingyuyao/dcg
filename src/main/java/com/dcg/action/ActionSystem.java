package com.dcg.action;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;

@All(Action.class)
public class ActionSystem extends IteratingSystem {
  protected ComponentMapper<Action> mAction;

  @Override
  protected void process(int actionEntity) {
    Action action = mAction.get(actionEntity);
    action.minInputCount = action.command.getMinInputCount();
    action.maxInputCount = action.command.getMaxInputCount();
    action.allowedInputs = action.command.getAllowedInputs();
  }
}
