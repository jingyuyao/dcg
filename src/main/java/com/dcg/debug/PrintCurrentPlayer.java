package com.dcg.debug;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.card.Card;
import com.dcg.card.Hand;
import com.dcg.command.Command;
import com.dcg.ownership.Owned;
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
    System.out.print("    " + mPlayer.get(playerEntity).name);
    Aspect.Builder hand = Aspect.all(Card.class, Owned.class, Hand.class);
    System.out.print(" Hand: ");
    for (int cardEntity : ownershipSystem.filter(hand, playerEntity)) {
      System.out.print(mCard.get(cardEntity).name + ", ");
    }
    System.out.println();
  }
}
