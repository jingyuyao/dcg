package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.annotations.Wire;
import com.dcg.action.Action;
import com.dcg.action.CreateAction;
import com.dcg.action.ExecuteAction;
import com.dcg.card.Card;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.location.Hand;
import com.dcg.ownership.OwnershipSystem;
import java.util.Collections;

/** Create an action that plays all cards and attach it to the player. */
public class CreatePlayAllCards extends Command {
  @Wire protected CommandChain commandChain;
  protected OwnershipSystem ownershipSystem;

  @Override
  protected void run() {
    commandChain.addEnd(new CreateAction(new PlayAllCards().setOwner(owner)).setOwner(owner));
  }

  private class PlayAllCards extends Command {
    @Override
    protected void run() {
      ownershipSystem
          .getOwnedBy(owner, Aspect.all(Card.class, Hand.class))
          .flatMap(cardEntity -> ownershipSystem.getOwnedBy(cardEntity, Aspect.all(Action.class)))
          .forEach(
              actionEntity -> {
                ExecuteAction executeAction = new ExecuteAction();
                executeAction.setInput(Collections.singletonList(actionEntity));
                commandChain.addEnd(executeAction);
              });
    }
  }
}
