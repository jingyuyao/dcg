package com.dcg.command;

import com.artemis.BaseSystem;
import com.artemis.SystemInvocationStrategy;
import com.artemis.annotations.Wire;

public class CommandExecutor extends SystemInvocationStrategy {
  @Wire protected CommandChain commandChain;

  /**
   * Returns whether the preconditions for this command is satisfied. Will automatically inject the
   * given command.
   */
  public boolean canExecute(Command command) {
    world.inject(command);
    return command.canExecute();
  }

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
      if (canExecute(command)) {
        command.execute();
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
