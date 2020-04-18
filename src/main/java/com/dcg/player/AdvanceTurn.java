package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.card.Card;
import com.dcg.command.Target;
import com.dcg.location.Hand;
import com.dcg.location.PlayArea;
import java.util.List;
import java.util.stream.Collectors;

public class AdvanceTurn extends PlayerEffect {
  protected ComponentMapper<Turn> mTurn;

  public AdvanceTurn() {
    addTargetConditions(
        target -> target.get().stream().allMatch(mTurn::has),
        target -> {
          int playerEntity = target.get().get(0);
          long cardsInHandCount =
              coreSystem.getChildren(playerEntity, Aspect.all(Card.class, Hand.class)).count();
          long cardsInPlayCount =
              coreSystem.getChildren(playerEntity, Aspect.all(Card.class, PlayArea.class)).count();
          // Check cardsInPlay so this doesn't get automatically triggered on enter.
          return cardsInHandCount == 0 && cardsInPlayCount != 0;
        });
  }

  @Override
  protected void run(Target target) {
    int playerEntity = target.get().get(0);
    List<Integer> allPlayerEntities =
        coreSystem.getStream(Aspect.all(Player.class)).boxed().collect(Collectors.toList());
    int currentPlayerIndex = allPlayerEntities.indexOf(playerEntity);
    int nextPlayerIndex = (currentPlayerIndex + 1) % allPlayerEntities.size();
    int nextPlayer = allPlayerEntities.get(nextPlayerIndex);
    mTurn.remove(playerEntity);
    mTurn.create(nextPlayer);
  }
}
