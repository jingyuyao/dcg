package com.dcg.action;

import com.artemis.Aspect;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.Target;

public class DeleteActions extends AbstractCommandBuilder {
  @Override
  protected void run(Target target) {
    target.get().stream()
        .flatMapToInt(entity -> coreSystem.getChildren(entity, Aspect.all(Action.class)))
        .forEach(world::delete);
  }
}
