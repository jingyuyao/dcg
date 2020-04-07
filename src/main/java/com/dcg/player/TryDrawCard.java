package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.utils.IntBag;
import com.dcg.card.Card;
import com.dcg.command.Command;
import com.dcg.command.CommandDeque;

public class TryDrawCard implements Command {

  private final int playerEntity;

  @Wire
  CommandDeque commandDeque;
  AspectSubscriptionManager manager;
  ComponentMapper<PlayerOwned> mPlayerOwned;
  ComponentMapper<DrawPile> mInDrawPile;
  ComponentMapper<Hand> mInHand;

  public TryDrawCard(int playerEntity) {
    this.playerEntity = playerEntity;
  }

  @Override
  public void run() {
    IntBag drawPile =
        manager.get(Aspect.all(Card.class, PlayerOwned.class, DrawPile.class)).getEntities();

    for (int i = 0, s = drawPile.size(); i < s; i++) {
      int cardEntity = drawPile.get(i);
      if (mPlayerOwned.get(cardEntity).playerEntity == playerEntity) {
        mInDrawPile.remove(cardEntity);
        mInHand.create(cardEntity);
        return;
      }
    }

    commandDeque.addFirst(new TryDrawCard(playerEntity));
    commandDeque.addFirst(new MoveDiscardPileToDrawPile(playerEntity));
  }

  @Override
  public String toString() {
    return "TryDrawCard{" +
        "playerEntity=" + playerEntity +
        '}';
  }
}
