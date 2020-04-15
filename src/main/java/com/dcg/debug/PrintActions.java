package com.dcg.debug;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.action.Action;
import com.dcg.card.Card;
import com.dcg.command.Command;
import com.dcg.location.ForgeRow;
import com.dcg.ownership.OwnershipSystem;
import com.dcg.turn.TurnSystem;
import com.dcg.util.AspectSystem;

public class PrintActions extends Command {
  protected AspectSystem aspectSystem;
  protected OwnershipSystem ownershipSystem;
  protected TurnSystem turnSystem;
  protected ComponentMapper<Action> mAction;

  @Override
  public void run() {
    int currentPlayerEntity = turnSystem.getCurrentPlayerEntity();
    ownershipSystem
        .getDescendants(currentPlayerEntity, Aspect.all(Action.class))
        .forEach(this::printAction);
    aspectSystem
        .getStream(Aspect.all(Card.class, ForgeRow.class))
        .flatMap(cardEntity -> ownershipSystem.getDescendants(cardEntity, Aspect.all(Action.class)))
        .forEach(this::printAction);
  }

  private void printAction(int actionEntity) {
    Action action = mAction.get(actionEntity);
    System.out.printf("    *%d %s\n", actionEntity, action.command);
  }
}
