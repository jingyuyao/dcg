package com.dcg.command;

import com.artemis.BaseSystem;
import com.artemis.SystemInvocationStrategy;
import com.artemis.annotations.Wire;
import com.dcg.game.Preconditions.GameStateException;

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
    while (!commandChain.isEmpty()) {
      Command command = commandChain.pop();
      updateEntityStates();
      if (command.canRun()) {
        try {
          CommandData data = command.run();
          CommandLog log = command.createLog(data);
          commandChain.log(log);
          System.out.printf("Exec: %s %s\n", log.getCommandName(), log.getDescription());
        } catch (GameStateException e) {
          System.out.printf("Exception: %s\n", e.getMessage());
        }
      } else {
        System.out.printf("Pass: %s\n", command.getClass().getSimpleName());
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
