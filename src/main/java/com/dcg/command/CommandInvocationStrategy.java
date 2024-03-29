package com.dcg.command;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.SystemInvocationStrategy;
import com.artemis.annotations.Wire;
import com.dcg.battle.Unit;
import com.dcg.card.Card;
import com.dcg.game.CoreSystem;
import com.dcg.game.Preconditions.GameStateException;

/** Steps: 1. Execute a command 2. Update systems 3. Go to 1 if command chain is not empty */
public class CommandInvocationStrategy extends SystemInvocationStrategy {
  @Wire protected CommandChain commandChain;
  protected CoreSystem coreSystem;
  protected ComponentMapper<Card> mCard;
  protected ComponentMapper<Unit> mUnit;

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
          // Note: ordering is important. We can to create the log before the command is executed
          // to capture information before entities are potentially deleted.
          CommandData data = command.getData();
          CommandLog log = createLog(command, data);
          command.run(data);
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

  private CommandLog createLog(Command command, CommandData data) {
    int currentPlayerEntity = coreSystem.getCurrentPlayerEntity();
    int originCardEntity = getCardEntity(data.getOriginEntity());
    return new CommandLog(
        command.getName(),
        command.getDescription(data),
        command.isClientVisible(data),
        currentPlayerEntity,
        originCardEntity);
  }

  private int getCardEntity(int originEntity) {
    // TODO: This is pretty hacky. We should likely wrap the originEntity in an object.
    if (originEntity == -1 || mCard.has(originEntity)) {
      return originEntity;
    } else if (mUnit.has(originEntity)) {
      return mUnit.get(originEntity).cardEntity;
    } else {
      return -1;
    }
  }
}
