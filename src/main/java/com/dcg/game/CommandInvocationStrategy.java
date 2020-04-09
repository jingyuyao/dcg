package com.dcg.game;

import com.artemis.BaseSystem;
import com.artemis.SystemInvocationStrategy;
import com.artemis.annotations.Wire;
import com.dcg.command.Command;
import com.dcg.command.CommandDeque;

public class CommandInvocationStrategy extends SystemInvocationStrategy {

  @Wire CommandDeque commandDeque;

  @Override
  protected void initialize() {
    super.initialize();
    world.inject(this);
  }

  @Override
  protected void process() {
    System.out.println("Processing");
    while (!commandDeque.isEmpty()) {
      Command command = commandDeque.remove();
      world.inject(command);
      System.out.println("  " + command.toString());
      updateEntityStates();
      command.run();
      processSystems();
    }
  }

  private void processSystems() {
    BaseSystem[] systemsData = systems.getData();
    for (int i = 0; i < systems.size(); i++) {
      if (disabled.get(i)) {
        continue;
      }

      updateEntityStates();
      systemsData[i].process();
    }
    updateEntityStates();
  }
}
