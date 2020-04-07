package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.ComponentMapper;
import com.artemis.utils.IntBag;
import com.dcg.card.Card;
import com.dcg.command.Command;

public class MoveDiscardPileToDrawPile implements Command {

  private final int playerEntity;
  AspectSubscriptionManager manager;
  ComponentMapper<PlayerOwned> mPlayerOwned;
  ComponentMapper<DiscardPile> mDiscardPile;
  ComponentMapper<DrawPile> mDrawPile;

  public MoveDiscardPileToDrawPile(int playerEntity) {
    this.playerEntity = playerEntity;
  }

  @Override
  public void run() {
    IntBag discardPile =
        manager.get(Aspect.all(Card.class, PlayerOwned.class, DiscardPile.class)).getEntities();

    for (int i = 0, s = discardPile.size(); i < s; i++) {
      int cardEntity = discardPile.get(i);
      if (mPlayerOwned.get(cardEntity).playerEntity == playerEntity) {
        mDiscardPile.remove(cardEntity);
        mDrawPile.create(cardEntity);
      }
    }
  }

  @Override
  public String toString() {
    return "MoveDiscardPileToDrawPile{" +
        "playerEntity=" + playerEntity +
        '}';
  }
}
