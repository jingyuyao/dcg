package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.BaseEntitySystem;
import com.artemis.EntitySubscription.SubscriptionListener;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.utils.IntBag;
import com.dcg.card.Card;
import com.dcg.command.CommandChain;
import com.dcg.game.CoreSystem;
import com.dcg.location.Hand;
import com.dcg.location.MoveLocation;
import com.dcg.location.PlayArea;

@All({Player.class, Turn.class})
public class PlayHandSystem extends BaseEntitySystem {
  @Wire protected CommandChain commandChain;
  protected AspectSubscriptionManager manager;
  protected CoreSystem coreSystem;

  @Override
  protected void initialize() {
    manager
        .get(Aspect.all(Card.class, Hand.class))
        .addSubscriptionListener(
            new SubscriptionListener() {
              @Override
              public void inserted(IntBag cardEntities) {
                int currentPlayerEntity =
                    coreSystem
                        .getStream(Aspect.all(Player.class, Turn.class))
                        .findFirst()
                        .orElse(-1);

                for (int i = 0; i < cardEntities.size(); i++) {
                  int cardEntity = cardEntities.get(i);
                  if (coreSystem.getOwner(cardEntity) == currentPlayerEntity) {
                    commandChain.addEnd(new MoveLocation(PlayArea.class).build(world, cardEntity));
                  }
                }
              }

              @Override
              public void removed(IntBag cardEntities) {}
            });
  }

  @Override
  protected void inserted(int playerEntity) {
    coreSystem
        .getOwnedBy(playerEntity, Aspect.all(Card.class, Hand.class))
        .forEach(
            cardEntity ->
                commandChain.addEnd(new MoveLocation(PlayArea.class).build(world, cardEntity)));
  }

  @Override
  protected void processSystem() {}
}
