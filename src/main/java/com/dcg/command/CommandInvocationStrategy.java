package com.dcg.command;

import com.artemis.BaseSystem;
import com.artemis.SystemInvocationStrategy;
import com.artemis.annotations.Wire;

/** Steps: 1. Execute a command 2. Update systems 3. Go to 1 if command chain is not empty */
public class CommandInvocationStrategy extends SystemInvocationStrategy {
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
      ExecutableCommand executableCommand = commandChain.pop();
      System.out.printf("  %s\n", executableCommand.toString());
      updateEntityStates();
      if (executableCommand.canRun()) {
        executableCommand.run();
      } else {
        System.out.printf("  %s ignored\n", executableCommand.toString());
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
