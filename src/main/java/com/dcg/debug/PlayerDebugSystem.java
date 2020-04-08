package com.dcg.debug;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.utils.IntBag;
import com.dcg.card.Card;
import com.dcg.card.Card.Location;
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

  public void printPlayers() {
    System.out.println("players");
    IntBag players = manager.get(Aspect.all(Player.class)).getEntities();
    for (int i = 0, s = players.size(); i < s; i++) {
      int playerEntity = players.get(i);
      System.out.print(mTurn.has(playerEntity) ? " *" : "  ");
      System.out.print(mPlayer.get(playerEntity).name);
      printHand(playerEntity);
      System.out.println();
    }
  }

  private void printHand(int playerEntity) {
    IntBag cards = manager.get(Aspect.all(Card.class, PlayerOwned.class)).getEntities();
    System.out.print(" hand: ");
    for (int i = 0, s = cards.size(); i < s; i++) {
      int cardEntity = cards.get(i);
      PlayerOwned owner = mPlayerOwned.get(cardEntity);
      Card card = mCard.get(cardEntity);
      if (owner.playerEntity == playerEntity && card.location == Location.HAND) {
        System.out.print(mCard.get(cardEntity).name + ", ");
      }
    }
  }
}
