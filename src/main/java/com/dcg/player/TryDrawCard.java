package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.utils.IntBag;
import com.dcg.card.Card;
import com.dcg.card.DrawPile;
import com.dcg.card.Hand;
import com.dcg.card.MoveLocation;
import com.dcg.command.Command;
import com.dcg.command.CommandDeque;

public class TryDrawCard implements Command {

  private final int playerEntity;

  @Wire CommandDeque commandDeque;
  AspectSubscriptionManager manager;
  ComponentMapper<Player> mPlayer;
  ComponentMapper<PlayerOwned> mPlayerOwned;

  public TryDrawCard(int playerEntity) {
    this.playerEntity = playerEntity;
  }

  @Override
  public void run() {
    IntBag drawPile =
        manager.get(Aspect.all(Card.class, PlayerOwned.class, DrawPile.class)).getEntities();

    for (int i = 0; i < drawPile.size(); i++) {
      int cardEntity = drawPile.get(i);
      if (playerEntity == mPlayerOwned.get(cardEntity).playerEntity) {
        commandDeque.addFirst(new MoveLocation(cardEntity, Hand.class));
        return;
      }
    }

    commandDeque.addFirst(new ReshuffleDiscard(playerEntity), new TryDrawCard(playerEntity));
  }

  @Override
  public String toString() {
    return "TryDrawCard " + mPlayer.get(playerEntity);
  }
}
