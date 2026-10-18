package com.dcg.command;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.SystemInvocationStrategy;
import com.artemis.annotations.Wire;
import com.dcg.battle.Unit;
import com.dcg.card.Card;
import com.dcg.game.CoreSystem;
import com.dcg.game.Preconditions.GameStateException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Steps: 1. Execute a command 2. Update systems 3. Go to 1 if command chain is not empty */
public class CommandInvocationStrategy extends SystemInvocationStrategy {
  private static final Logger logger = LoggerFactory.getLogger(CommandInvocationStrategy.class);
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
          logger.debug("Exec: {} {}", log.getCommandName(), log.getDescription());
        } catch (GameStateException e) {
          logger.warn("Exception: {}", e.getMessage());
        }
      } else {
        logger.debug("Pass: {}", command.getName());
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
