package com.dcg.command;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;

public class CommandSystem extends BaseSystem {
  @Wire Commands commands;

  @Override
  protected void processSystem() {
    for (Command command : commands.getCurrent()) {
      world.inject(command);
      command.run();
    }
  }
}
