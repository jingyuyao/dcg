package com.dcg.action;

import com.artemis.Aspect;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.target.Target;

public class DeleteActions extends AbstractCommandBuilder {
  @Override
  protected void run(Target target) {
    coreSystem.getChildren(target.getOrigin(), Aspect.all(Action.class)).forEach(world::delete);
  }
}
