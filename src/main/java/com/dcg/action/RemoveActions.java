package com.dcg.action;

import com.artemis.Aspect;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandData;

public class RemoveActions extends AbstractCommandBuilder {
  @Override
  protected void run(CommandData data) {
    coreSystem
        .getChildren(data.getOriginEntity(), Aspect.all(Action.class))
        .forEach(world::delete);
  }

  @Override
  protected String getDescription(CommandData data) {
    return String.format("for %s", coreSystem.toName(data.getOriginEntity()));
  }
}
