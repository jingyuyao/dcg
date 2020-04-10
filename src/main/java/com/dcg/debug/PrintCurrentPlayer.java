package com.dcg.debug;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.card.Card;
import com.dcg.command.Command;
import com.dcg.location.Hand;
import com.dcg.ownership.OwnershipSystem;
import com.dcg.player.Player;
import com.dcg.turn.TurnSystem;

public class PrintCurrentPlayer extends Command {
  TurnSystem turnSystem;
  OwnershipSystem ownershipSystem;
  ComponentMapper<Player> mPlayer;
  ComponentMapper<Card> mCard;

  @Override
  public void run() {
    int playerEntity = turnSystem.getCurrentPlayerEntity();
    Player player = mPlayer.get(playerEntity);
    System.out.print("    " + player + " hp: " + player.hp);
    Aspect.Builder hand = Aspect.all(Card.class, Hand.class);
    System.out.print(" Hand: ");
    for (int cardEntity : ownershipSystem.getOwnedBy(hand, playerEntity)) {
      System.out.print(mCard.get(cardEntity) + ", ");
    }
    System.out.println();
  }
}
