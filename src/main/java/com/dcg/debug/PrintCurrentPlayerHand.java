package com.dcg.debug;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.card.Card;
import com.dcg.location.Hand;
import com.dcg.ownership.OwnershipSystem;
import com.dcg.turn.TurnSystem;

public class PrintCurrentPlayerHand extends DebugEntityCommand {
  protected OwnershipSystem ownershipSystem;
  protected TurnSystem turnSystem;
  protected ComponentMapper<Card> mCard;

  @Override
  public void run() {
    ownershipSystem
        .getOwnedBy(turnSystem.getPlayerEntity(), Aspect.all(Card.class, Hand.class))
        .forEach(this::printCard);
  }

  private void printCard(int cardEntity) {
    System.out.printf("    *%d %s\n", cardEntity, mCard.get(cardEntity));
    printActions(cardEntity);
  }
}
