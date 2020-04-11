package com.dcg.debug;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.ComponentMapper;
import com.artemis.utils.IntBag;
import com.dcg.card.Card;
import com.dcg.command.Command;
import com.dcg.location.Hand;
import com.dcg.ownership.OwnershipSystem;
import com.dcg.player.Player;

public class PrintPlayers extends Command {
  AspectSubscriptionManager manager;
  OwnershipSystem ownershipSystem;
  ComponentMapper<Player> mPlayer;
  ComponentMapper<Card> mCard;

  @Override
  public void run() {
    IntBag playerEntities = manager.get(Aspect.all(Player.class)).getEntities();
    for (int i = 0; i < playerEntities.size(); i++) {
      int playerEntity = playerEntities.get(i);
      printPlayer(playerEntity);
    }
  }

  private void printPlayer(int playerEntity) {
    Player player = mPlayer.get(playerEntity);
    System.out.printf("    *%d %s hp: %d Hand: ", playerEntity, player.name, player.hp);
    Aspect.Builder hand = Aspect.all(Card.class, Hand.class);
    for (int cardEntity : ownershipSystem.getOwnedBy(playerEntity, hand)) {
      System.out.printf("%s, ", mCard.get(cardEntity));
    }
    System.out.println();
  }
}
