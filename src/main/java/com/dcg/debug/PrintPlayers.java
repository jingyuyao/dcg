package com.dcg.debug;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.card.Card;
import com.dcg.command.Command;
import com.dcg.location.Hand;
import com.dcg.ownership.OwnershipSystem;
import com.dcg.player.Player;
import com.dcg.util.AspectSystem;

public class PrintPlayers extends Command {
  protected AspectSystem aspectSystem;
  protected OwnershipSystem ownershipSystem;
  protected ComponentMapper<Player> mPlayer;
  protected ComponentMapper<Card> mCard;

  @Override
  public void run() {
    for (int playerEntity : aspectSystem.get(Aspect.all(Player.class))) {
      printPlayer(playerEntity);
    }
  }

  private void printPlayer(int playerEntity) {
    System.out.printf("    *%d %s Hand: ", playerEntity, mPlayer.get(playerEntity));
    Aspect.Builder hand = Aspect.all(Card.class, Hand.class);
    for (int cardEntity : ownershipSystem.getOwnedBy(playerEntity, hand)) {
      System.out.printf("%s, ", mCard.get(cardEntity));
    }
    System.out.println();
  }
}
