package com.dcg.card;

import com.dcg.command.AbstractCommandBuilder;
import com.dcg.target.Target;

public class DeleteCard extends AbstractCommandBuilder {
  @Override
  protected void run(Target target) {
    target.getTargets().forEach(world::delete);
  }
}
