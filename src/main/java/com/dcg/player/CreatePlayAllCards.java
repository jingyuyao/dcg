package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.dcg.action.Action;
import com.dcg.action.CreateAction;
import com.dcg.action.ExecuteAction;
import com.dcg.card.Card;
import com.dcg.command.CommandBase;
import com.dcg.command.CommandChain;
import com.dcg.command.ExecutableCommand;
import com.dcg.game.OwnershipSystem;
import com.dcg.location.Hand;
import java.util.Collections;

/** Create an action that plays all cards and attach it to the player. */
public class CreatePlayAllCards extends CommandBase {
  @Wire protected CommandChain commandChain;
  protected OwnershipSystem ownershipSystem;
  protected ComponentMapper<Action> mAction;

  @Override
  protected void run() {
    commandChain.addEnd(new CreateAction(new PlayAllCards()).build(world, sourceEntity));
  }

  private class PlayAllCards extends CommandBase {
    @Override
    protected void run() {
      ownershipSystem
          .getOwnedBy(sourceEntity, Aspect.all(Card.class, Hand.class))
          .flatMap(cardEntity -> ownershipSystem.getOwnedBy(cardEntity, Aspect.all(Action.class)))
          .forEach(
              actionEntity -> {
                ExecutableCommand executableCommand = new ExecuteAction().build(world, -1);
                executableCommand.setInput(Collections.singletonList(actionEntity));
                commandChain.addEnd(executableCommand);
              });
    }
  }
}
