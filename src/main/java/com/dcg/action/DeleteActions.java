package com.dcg.action;

import com.artemis.Aspect;
import com.dcg.command.AbstractCommandBuilder;

public class DeleteActions extends AbstractCommandBuilder {;

  @Override
  protected void run() {
    coreSystem.getChildren(sourceEntity, Aspect.all(Action.class)).forEach(world::delete);
  }
}
