package com.dcg.debug;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.card.Card;
import com.dcg.game.CoreSystem;
import com.dcg.location.ForgeRow;

public class PrintForgeRow extends DebugEntityCommand {
  protected CoreSystem coreSystem;
  protected ComponentMapper<Card> mCard;

  @Override
  protected void run() {
    coreSystem.getStream(Aspect.all(Card.class, ForgeRow.class)).forEach(this::printCard);
  }

  private void printCard(int cardEntity) {
    System.out.printf("    *%d %s\n", cardEntity, mCard.get(cardEntity));
    printActions(cardEntity);
  }
}
