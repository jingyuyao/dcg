package com.dcg.debug;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.card.Card;
import com.dcg.command.Target;
import com.dcg.location.ForgeRow;

public class PrintForgeRow extends DebugEntityCommand {
  protected ComponentMapper<Card> mCard;

  @Override
  protected void run(Target target) {
    coreSystem.getStream(Aspect.all(Card.class, ForgeRow.class)).forEach(this::printCard);
  }

  private void printCard(int cardEntity) {
    System.out.printf("    *%d %s %s\n", cardEntity, name(cardEntity), mCard.get(cardEntity));
    printActions(cardEntity);
  }
}
