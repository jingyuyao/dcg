package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.utils.IntBag;
import com.dcg.card.Card;
import com.dcg.card.Card.Location;
import com.dcg.command.Command;
import com.dcg.command.CommandDeque;

public class TryDrawCard implements Command {

  private final int playerEntity;

  @Wire
  CommandDeque commandDeque;
  AspectSubscriptionManager manager;
  ComponentMapper<PlayerOwned> mPlayerOwned;
  ComponentMapper<Card> mCard;

  public TryDrawCard(int playerEntity) {
    this.playerEntity = playerEntity;
  }

  @Override
  public void run() {
    IntBag cards = manager.get(Aspect.all(Card.class, PlayerOwned.class)).getEntities();

    for (int i = 0, s = cards.size(); i < s; i++) {
      int cardEntity = cards.get(i);
      PlayerOwned owner = mPlayerOwned.get(cardEntity);
      Card card = mCard.get(cardEntity);
      if (owner.playerEntity == playerEntity && card.location == Location.DRAW_PILE) {
        card.location = Location.HAND;
        return;
      }
    }

    commandDeque.addFirst(new TryDrawCard(playerEntity));
    commandDeque.addFirst(new MoveDiscardPileToDrawPile(playerEntity));
  }

  @Override
  public String toString() {
    return "TryDrawCard{" + "playerEntity=" + playerEntity + '}';
  }
}
