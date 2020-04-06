package com.dcg.game;

import com.artemis.BaseSystem;
import com.artemis.SystemInvocationStrategy;
import com.artemis.annotations.Wire;
import com.dcg.command.Command;
import com.dcg.command.CommandQueue;

public class CommandInvocationStrategy extends SystemInvocationStrategy {

  @Wire
  CommandQueue commandQueue;

  @Override
  protected void initialize() {
    super.initialize();
    world.inject(this);
  }

  @Override
  protected void process() {
    while (!commandQueue.isEmpty()) {
      Command command = commandQueue.remove();
      world.inject(command);
      System.out.println("running: " + command.toString());
      command.run();
      processSystems();
    }
  }

  private void processSystems() {
    BaseSystem[] systemsData = systems.getData();
    for (int i = 0, s = systems.size(); s > i; i++) {
      if (disabled.get(i)) {
        continue;
      }

      updateEntityStates();
      systemsData[i].process();
    }
    updateEntityStates();
  }
}
