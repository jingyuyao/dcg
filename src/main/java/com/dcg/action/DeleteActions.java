package com.dcg.action;

import com.artemis.Aspect;
import com.dcg.command.AbstractCommandBuilder;
import java.util.List;

public class DeleteActions extends AbstractCommandBuilder {;

  @Override
  protected void run(List<Integer> input) {
    coreSystem.getChildren(sourceEntity, Aspect.all(Action.class)).forEach(world::delete);
  }
}
