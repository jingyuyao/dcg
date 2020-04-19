package com.dcg.debug;

import com.artemis.Aspect;
import com.dcg.action.Action;
import com.dcg.command.Target;

public class PrintActions extends DebugEntityCommand {
  @Override
  protected void run(Target target) {
    coreSystem.getStream(Aspect.all(Action.class)).forEach(this::printAction);
  }
}
