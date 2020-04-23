package com.dcg.player;

import com.artemis.Aspect;
import com.dcg.card.Card;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.location.Hand;
import com.dcg.location.PlayArea;
import java.util.List;

public class AdvanceTurn extends AbstractCommandBuilder {
  public AdvanceTurn() {
    addTriggerConditions(
        playerEntity -> {
          long cardsInHandCount =
              coreSystem.getChildren(playerEntity, Aspect.all(Card.class, Hand.class)).count();
          long cardsInPlayCount =
              coreSystem.getChildren(playerEntity, Aspect.all(Card.class, PlayArea.class)).count();
          // Check cardsInPlay so this doesn't get automatically triggered on enter.
          return cardsInHandCount == 0 && cardsInPlayCount != 0;
        });
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, int value) {
    // Since we only allow adding things to the end of the chain, each step is responsible for
    // adding its required actions and then invoking the next step. The general flow is:
    // - BattleStep
    // - CleanUpStep
    // - AdvancePlayerStep
    commandChain.addEnd(new BattleStep().build(world, originEntity));
  }
}
