package com.dcg.debug;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.utils.IntBag;
import com.dcg.card.Card;
import com.dcg.card.Hand;
import com.dcg.ownership.Owned;
import com.dcg.player.Player;
import com.dcg.turn.Turn;

public class PlayerDebugSystem extends BaseSystem {

  AspectSubscriptionManager manager;
  ComponentMapper<Player> mPlayer;
  ComponentMapper<Owned> mPlayerOwned;
  ComponentMapper<Turn> mTurn;
  ComponentMapper<Card> mCard;

  @Override
  protected void processSystem() {
    // No-op
  }

  public void printPlayers() {
    System.out.println("players");
    IntBag players = manager.get(Aspect.all(Player.class)).getEntities();
    for (int i = 0; i < players.size(); i++) {
      int playerEntity = players.get(i);
      System.out.print(mTurn.has(playerEntity) ? " *" : "  ");
      System.out.print(mPlayer.get(playerEntity).name);
      printHand(playerEntity);
      System.out.println();
    }
  }

  private void printHand(int playerEntity) {
    IntBag hand = manager.get(Aspect.all(Card.class, Owned.class, Hand.class)).getEntities();
    System.out.print(" hand: ");
    for (int i = 0; i < hand.size(); i++) {
      int cardEntity = hand.get(i);
      if (playerEntity == mPlayerOwned.get(cardEntity).owner) {
        System.out.print(mCard.get(cardEntity).name + ", ");
      }
    }
  }
}
