package com.dcg.debug;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.card.Card;
import com.dcg.game.AspectSystem;
import com.dcg.location.PlayArea;

public class PrintPlayArea extends DebugEntityCommand {
  protected AspectSystem aspectSystem;
  protected ComponentMapper<Card> mCard;

  @Override
  protected void run() {
    aspectSystem.getStream(Aspect.all(Card.class, PlayArea.class)).forEach(this::printCard);
  }

  private void printCard(int cardEntity) {
    System.out.printf("    *%d %s\n", cardEntity, mCard.get(cardEntity));
    printActions(cardEntity);
  }
}
