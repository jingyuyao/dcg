package com.dcg.player;

import static com.dcg.action.CreateAction.action;

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
import com.dcg.turn.Turn;

@All({Player.class, Turn.class})
public class AddPlayCardSystem extends BaseEntitySystem {
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
                int currentPlayerEntity = coreSystem.getCurrentPlayerEntity();
                for (int i = 0; i < cardEntities.size(); i++) {
                  int cardEntity = cardEntities.get(i);
                  if (coreSystem.getParent(cardEntity) == currentPlayerEntity) {
                    commandChain.addEnd(action("Play", new PlayCard()).build(world, cardEntity));
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
        .getChildren(playerEntity, Aspect.all(Card.class, Hand.class))
        .forEach(
            cardEntity ->
                commandChain.addEnd(action("Play", new PlayCard()).build(world, cardEntity)));
  }

  @Override
  protected void processSystem() {}
}
