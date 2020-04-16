package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.ComponentMapper;
import com.artemis.utils.IntBag;
import com.dcg.card.Card;
import com.dcg.command.Command;
import com.dcg.location.Hand;
import com.dcg.ownership.OwnershipSystem;
import com.dcg.turn.Turn;
import com.dcg.turn.TurnSystem;

public class AdvanceTurn extends Command {
  protected AspectSubscriptionManager manager;
  protected TurnSystem turnSystem;
  protected OwnershipSystem ownershipSystem;
  protected ComponentMapper<Turn> mTurn;

  @Override
  protected boolean canRun() {
    return ownershipSystem
            .getOwnedBy(turnSystem.getPlayerEntity(), Aspect.all(Card.class, Hand.class))
            .count()
        == 0;
  }

  @Override
  protected void run() {
    int currentPlayer = manager.get(Aspect.all(Player.class, Turn.class)).getEntities().get(0);
    IntBag players = manager.get(Aspect.all(Player.class)).getEntities();
    int currentPlayerIndex = players.indexOf(currentPlayer);
    int nextPlayerIndex = (currentPlayerIndex + 1) % players.size();
    int nextPlayer = players.get(nextPlayerIndex);
    mTurn.remove(currentPlayer);
    Turn turn = mTurn.create(nextPlayer);
    turn.previousPlayerEntity = currentPlayer;
  }
}
