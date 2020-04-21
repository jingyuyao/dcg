package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.card.Card;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.location.Hand;
import com.dcg.location.PlayArea;
import com.dcg.target.Target;
import java.util.List;
import java.util.stream.Collectors;
import net.mostlyoriginal.api.utils.Preconditions;

public class AdvanceTurn extends AbstractCommandBuilder {
  protected ComponentMapper<Turn> mTurn;

  public AdvanceTurn() {
    addTargetConditions(
        target -> {
          int playerEntity = coreSystem.getCurrentPlayerEntity().findFirst().orElse(-1);
          long cardsInHandCount =
              coreSystem.getChildren(playerEntity, Aspect.all(Card.class, Hand.class)).count();
          long cardsInPlayCount =
              coreSystem.getChildren(playerEntity, Aspect.all(Card.class, PlayArea.class)).count();
          // Check cardsInPlay so this doesn't get automatically triggered on enter.
          Preconditions.checkArgument(
              cardsInHandCount == 0 && cardsInPlayCount != 0, "All cards must be played");
        });
  }

  @Override
  protected void run(Target target) {
    int playerEntity = coreSystem.getCurrentPlayerEntity().findFirst().orElse(-1);
    List<Integer> allPlayerEntities =
        coreSystem.getStream(Aspect.all(Player.class)).boxed().collect(Collectors.toList());
    int currentPlayerIndex = allPlayerEntities.indexOf(playerEntity);
    int nextPlayerIndex = (currentPlayerIndex + 1) % allPlayerEntities.size();
    int nextPlayer = allPlayerEntities.get(nextPlayerIndex);
    mTurn.remove(playerEntity);
    mTurn.create(nextPlayer);
  }
}
