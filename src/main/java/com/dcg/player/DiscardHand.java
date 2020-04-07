package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.ComponentMapper;
import com.artemis.utils.IntBag;
import com.dcg.card.Card;
import com.dcg.command.Command;

public class DiscardHand implements Command {

  private final int playerEntity;
  AspectSubscriptionManager manager;
  ComponentMapper<PlayerOwned> mPlayerOwned;
  ComponentMapper<Hand> mHand;
  ComponentMapper<DiscardPile> mDiscardPile;

  public DiscardHand(int playerEntity) {
    this.playerEntity = playerEntity;
  }

  @Override
  public void run() {
    IntBag hand =
        manager.get(Aspect.all(Card.class, PlayerOwned.class, Hand.class)).getEntities();

    for (int i = 0, s = hand.size(); i < s; i++) {
      int cardEntity = hand.get(i);
      if (mPlayerOwned.get(cardEntity).playerEntity == playerEntity) {
        mHand.remove(cardEntity);
        mDiscardPile.create(cardEntity);
      }
    }
  }

  @Override
  public String toString() {
    return "DiscardHand{" +
        "playerEntity=" + playerEntity +
        '}';
  }
}
