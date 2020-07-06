package com.dcg.turn;

import com.artemis.Aspect;
import com.dcg.card.Card;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandData;
import com.dcg.location.Hand;
import com.dcg.location.PlayArea;

public class AdvanceTurn extends AbstractCommandBuilder {
  public AdvanceTurn() {
    addTriggerConditions(
        (playerEntity, allowedTargets) -> coreSystem.getActivePlayerEntities().count() > 1,
        (playerEntity, allowedTargets) -> {
          long cardsInHandCount =
              coreSystem.getChildren(playerEntity, Aspect.all(Card.class, Hand.class)).count();
          long cardsInPlayCount =
              coreSystem.getChildren(playerEntity, Aspect.all(Card.class, PlayArea.class)).count();
          // Check cardsInPlay so this doesn't get automatically triggered on enter.
          return cardsInHandCount == 0 && cardsInPlayCount != 0;
        });
  }

  @Override
  protected void run(CommandData data) {
    // Since we only allow adding things to the end of the chain, each step is responsible for
    // adding its required actions and then invoking the next step. The general flow is:
    // - BattleStep
    // - CleanUpStep
    // - AdvancePlayerStep
    commandChain.addEnd(new BattleStep().build(world, data.getOriginEntity()));
  }
}
