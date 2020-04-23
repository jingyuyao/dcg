package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.card.Card;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.location.Hand;
import com.dcg.location.PlayArea;
import java.util.List;
import java.util.stream.Collectors;

public class AdvanceTurn extends AbstractCommandBuilder {
  protected ComponentMapper<Turn> mTurn;

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
    List<Integer> allPlayerEntities =
        coreSystem.getStream(Aspect.all(Player.class)).collect(Collectors.toList());
    int currentPlayerIndex = allPlayerEntities.indexOf(originEntity);
    int nextPlayerIndex = (currentPlayerIndex + 1) % allPlayerEntities.size();
    int nextPlayer = allPlayerEntities.get(nextPlayerIndex);
    mTurn.remove(originEntity);
    mTurn.create(nextPlayer);
  }
}
