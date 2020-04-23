package com.dcg.action;

import com.artemis.Aspect;
import com.dcg.command.AbstractCommandBuilder;
import java.util.List;

public class DeleteActions extends AbstractCommandBuilder {
  @Override
  protected void run(int originEntity, List<Integer> targets) {
    coreSystem.getChildren(originEntity, Aspect.all(Action.class)).forEach(world::delete);
  }
}
