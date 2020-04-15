package com.dcg.forge;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.BaseEntitySystem;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.utils.IntBag;
import com.dcg.action.CreateAction;
import com.dcg.card.Card;
import com.dcg.command.CommandChain;
import com.dcg.location.Deck;
import com.dcg.location.ForgeRow;
import com.dcg.location.MoveLocation;
import com.dcg.ownership.Owned;
import java.util.Random;

@All({Card.class, ForgeRow.class})
public class ForgeRowRefillSystem extends BaseEntitySystem {
  private static final int BUY_PILE_SIZE = 6;

  @Wire protected CommandChain commandChain;
  @Wire protected Random random;
  protected AspectSubscriptionManager manager;

  @Override
  protected void processSystem() {
    IntBag drawPile =
        manager.get(Aspect.all(Card.class, Deck.class).exclude(Owned.class)).getEntities();
    // We only need to add one since adding a move location command will trigger this system to
    // be run again.
    if (getEntityIds().size() < BUY_PILE_SIZE && drawPile.size() > 0) {
      int cardEntity = drawPile.get(random.nextInt(drawPile.size()));
      commandChain.addStart(
          new MoveLocation(cardEntity, ForgeRow.class),
          new CreateAction(cardEntity, new BuyCard(cardEntity)));
    }
  }
}
