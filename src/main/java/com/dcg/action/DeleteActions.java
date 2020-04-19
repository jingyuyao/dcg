package com.dcg.action;

import com.artemis.Aspect;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.Target;

public class DeleteActions extends AbstractCommandBuilder {
  @Override
  protected void run(Target target) {
    coreSystem.getChildren(target.getFrom(), Aspect.all(Action.class)).forEach(world::delete);
  }
}
