package com.dcg.forge;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.BaseEntitySystem;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.utils.IntBag;
import com.dcg.card.Card;
import com.dcg.card.Deck;
import com.dcg.card.ForgeRow;
import com.dcg.card.MoveLocation;
import com.dcg.command.CommandChain;
import com.dcg.player.PlayerOwned;

@All({Card.class, ForgeRow.class})
public class ForgeRowRefillSystem extends BaseEntitySystem {

  private static final int BUY_PILE_SIZE = 6;

  @Wire CommandChain commandChain;
  AspectSubscriptionManager manager;

  @Override
  protected void processSystem() {
    IntBag drawPile =
        manager.get(Aspect.all(Card.class, Deck.class).exclude(PlayerOwned.class)).getEntities();
    // We only need to add one since adding a move location command will trigger this system to
    // be run again.
    if (getEntityIds().size() < BUY_PILE_SIZE) {
      if (drawPile.size() > 0) {
        commandChain.addStart(new MoveLocation(drawPile.get(0), ForgeRow.class));
      } else {
        throw new RuntimeException("GG");
      }
    }
  }
}
