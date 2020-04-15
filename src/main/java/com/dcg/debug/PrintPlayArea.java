package com.dcg.debug;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.card.Card;
import com.dcg.location.PlayArea;
import com.dcg.util.AspectSystem;

public class PrintPlayArea extends DebugEntityCommand {
  protected AspectSystem aspectSystem;
  protected ComponentMapper<Card> mCard;

  @Override
  public void run() {
    aspectSystem.getStream(Aspect.all(Card.class, PlayArea.class)).forEach(this::printCard);
  }

  private void printCard(int cardEntity) {
    System.out.printf("    *%d %s\n", cardEntity, mCard.get(cardEntity));
    printActions(cardEntity);
  }
}
