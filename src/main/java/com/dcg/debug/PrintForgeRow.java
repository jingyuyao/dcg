package com.dcg.debug;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.card.Card;
import com.dcg.game.OwnershipSystem;
import com.dcg.location.ForgeRow;

public class PrintForgeRow extends DebugEntityCommand {
  protected OwnershipSystem ownershipSystem;
  protected ComponentMapper<Card> mCard;

  @Override
  protected void run() {
    ownershipSystem.getStream(Aspect.all(Card.class, ForgeRow.class)).forEach(this::printCard);
  }

  private void printCard(int cardEntity) {
    System.out.printf("    *%d %s\n", cardEntity, mCard.get(cardEntity));
    printActions(cardEntity);
  }
}
