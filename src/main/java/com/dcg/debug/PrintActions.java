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
import java.util.List;

public class PrintActions extends Command {
  protected AspectSystem aspectSystem;
  protected OwnershipSystem ownershipSystem;
  protected TurnSystem turnSystem;
  protected ComponentMapper<Action> mAction;

  @Override
  public void run() {
    // TODO: yikes, clean this up
    List<Integer> forgeRowEntities = aspectSystem.get(Aspect.all(Card.class, ForgeRow.class));
    int currentPlayerEntity = turnSystem.getCurrentPlayerEntity();
    for (int actionEntity : aspectSystem.get(Aspect.all(Action.class))) {
      if (ownershipSystem.isOwnedBy(currentPlayerEntity, actionEntity)) {
        Action action = mAction.get(actionEntity);
        System.out.printf("    *%d %s\n", actionEntity, action.command);
      } else {
        for (int cardEntity : forgeRowEntities) {
          if (ownershipSystem.isOwnedBy(cardEntity, actionEntity)) {
            Action action = mAction.get(actionEntity);
            System.out.printf("    *%d %s\n", actionEntity, action.command);
          }
        }
      }
    }
  }
}
