package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.utils.IntBag;
import com.dcg.card.Card;
import com.dcg.card.DiscardPile;
import com.dcg.card.DrawPile;
import com.dcg.card.MoveLocation;
import com.dcg.command.Command;
import com.dcg.command.CommandDeque;

public class ReshuffleDiscard implements Command {

  private final int playerEntity;
  @Wire CommandDeque commandDeque;
  AspectSubscriptionManager manager;
  ComponentMapper<Player> mPlayer;
  ComponentMapper<PlayerOwned> mPlayerOwned;

  public ReshuffleDiscard(int playerEntity) {
    this.playerEntity = playerEntity;
  }

  @Override
  public void run() {
    IntBag discardPile =
        manager.get(Aspect.all(Card.class, PlayerOwned.class, DiscardPile.class)).getEntities();

    for (int i = 0; i < discardPile.size(); i++) {
      int cardEntity = discardPile.get(i);
      if (playerEntity == mPlayerOwned.get(cardEntity).playerEntity) {
        commandDeque.addFirst(new MoveLocation(cardEntity, DrawPile.class));
      }
    }
  }

  @Override
  public String toString() {
    return "MoveDiscardPileToDrawPile " + mPlayer.get(playerEntity);
  }
}
