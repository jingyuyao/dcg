package com.dcg.debug;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.utils.IntBag;
import com.dcg.card.Card;
import com.dcg.player.Hand;
import com.dcg.player.Player;
import com.dcg.player.PlayerOwned;
import com.dcg.turn.Turn;

public class PlayerDebugSystem extends BaseSystem {

  AspectSubscriptionManager manager;
  ComponentMapper<Player> mPlayer;
  ComponentMapper<PlayerOwned> mPlayerOwned;
  ComponentMapper<Turn> mTurn;
  ComponentMapper<Card> mCard;

  @Override
  protected void processSystem() {
    // No-op
  }

  public void printDebug() {
    System.out.println("players");
    IntBag players = manager.get(Aspect.all(Player.class)).getEntities();
    IntBag hand = manager.get(Aspect.all(Card.class, PlayerOwned.class, Hand.class)).getEntities();
    for (int i = 0, s = players.size(); i < s; i++) {
      int playerEntity = players.get(i);
      System.out.print(mTurn.has(playerEntity) ? " *" : "  ");
      System.out.print(playerEntity + " ");
      System.out.print(mPlayer.get(playerEntity).name);
      System.out.print(" hand: ");
      for (int ii = 0, ss = hand.size(); ii < ss; ii++) {
        int cardEntity = hand.get(ii);
        if (mPlayerOwned.get(cardEntity).playerEntity == playerEntity) {
          System.out.print(mCard.get(cardEntity).name + ", ");
        }
      }
      System.out.println();
    }
  }
}
