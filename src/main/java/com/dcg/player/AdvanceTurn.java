package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.ComponentMapper;
import com.artemis.utils.IntBag;
import com.dcg.card.Card;
import com.dcg.command.CommandBase;
import com.dcg.game.OwnershipSystem;
import com.dcg.location.Hand;
import com.dcg.location.PlayArea;

public class AdvanceTurn extends CommandBase {
  protected AspectSubscriptionManager manager;
  protected OwnershipSystem ownershipSystem;
  protected ComponentMapper<Turn> mTurn;

  @Override
  protected boolean isWorldValid() {
    long cardsInHandCount =
        ownershipSystem.getOwnedBy(sourceEntity, Aspect.all(Card.class, Hand.class)).count();
    long cardsInPlayCount =
        ownershipSystem.getOwnedBy(sourceEntity, Aspect.all(Card.class, PlayArea.class)).count();
    // Check cardsInPlay so this doesn't get automatically triggered on enter.
    return cardsInHandCount == 0 && cardsInPlayCount != 0;
  }

  @Override
  protected void run() {
    int currentPlayer = manager.get(Aspect.all(Player.class, Turn.class)).getEntities().get(0);
    IntBag players = manager.get(Aspect.all(Player.class)).getEntities();
    int currentPlayerIndex = players.indexOf(currentPlayer);
    int nextPlayerIndex = (currentPlayerIndex + 1) % players.size();
    int nextPlayer = players.get(nextPlayerIndex);
    mTurn.remove(currentPlayer);
    mTurn.create(nextPlayer);
  }
}
