package com.dcg.debug;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.card.Card;
import com.dcg.location.ForgeRow;
import java.util.List;

public class PrintForgeRow extends DebugEntityCommand {;
  protected ComponentMapper<Card> mCard;

  @Override
  protected void run(List<Integer> input) {
    coreSystem.getStream(Aspect.all(Card.class, ForgeRow.class)).forEach(this::printCard);
  }

  private void printCard(int cardEntity) {
    System.out.printf("    *%d %s\n", cardEntity, mCard.get(cardEntity));
    printActions(cardEntity);
  }
}
