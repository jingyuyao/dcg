package com.dcg.command;

import com.artemis.BaseSystem;
import com.artemis.SystemInvocationStrategy;
import com.artemis.annotations.Wire;

public class CommandExecutor extends SystemInvocationStrategy {
  @Wire protected CommandChain commandChain;

  @Override
  protected void initialize() {
    super.initialize();
    world.inject(this);
  }

  @Override
  protected void process() {
    System.out.println("Executing");
    while (!commandChain.isEmpty()) {
      Command command = commandChain.pop();
      System.out.printf("  %s\n", command.toString());
      updateEntityStates();
      if (command.canRun(world)) {
        command.run(world);
      } else {
        System.out.printf("  %s ignored\n", command.toString());
      }
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
