package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.ComponentMapper;
import com.artemis.utils.IntBag;
import com.dcg.card.Card;
import com.dcg.card.Card.Location;
import com.dcg.command.Command;

public class MoveDiscardPileToDrawPile implements Command {

  private final int playerEntity;
  AspectSubscriptionManager manager;
  ComponentMapper<Player> mPlayer;
  ComponentMapper<PlayerOwned> mPlayerOwned;
  ComponentMapper<Card> mCard;

  public MoveDiscardPileToDrawPile(int playerEntity) {
    this.playerEntity = playerEntity;
  }

  @Override
  public void run() {
    IntBag cards = manager.get(Aspect.all(Card.class, PlayerOwned.class)).getEntities();

    for (int i = 0, s = cards.size(); i < s; i++) {
      int cardEntity = cards.get(i);
      if (mPlayerOwned.get(cardEntity).playerEntity == playerEntity) {
        mCard.get(cardEntity).location = Location.DRAW_PILE;
      }
    }
  }

  @Override
  public String toString() {
    return "MoveDiscardPileToDrawPile " + mPlayer.get(playerEntity);
  }
}
