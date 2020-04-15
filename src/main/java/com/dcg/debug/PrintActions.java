package com.dcg.debug;

import com.artemis.Aspect;
import com.artemis.Aspect.Builder;
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
    Builder actionBuilder = Aspect.all(Action.class);
    List<Integer> forgeRowEntities = aspectSystem.get(Aspect.all(Card.class, ForgeRow.class));
    int currentPlayerEntity = turnSystem.getCurrentPlayerEntity();
    for (int actionEntity : ownershipSystem.getDescendants(currentPlayerEntity, actionBuilder)) {
      Action action = mAction.get(actionEntity);
      System.out.printf("    *%d %s\n", actionEntity, action.command);
    }
    for (int cardEntity : forgeRowEntities) {
      for (int actionEntity : ownershipSystem.getDescendants(cardEntity, actionBuilder)) {
        Action action = mAction.get(actionEntity);
        System.out.printf("    *%d %s\n", actionEntity, action.command);
      }
    }
  }
}
