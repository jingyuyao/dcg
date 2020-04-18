package com.dcg.debug;

import com.artemis.Aspect;
import com.dcg.card.Card;
import com.dcg.command.Target;
import com.dcg.location.ForgeRow;

public class PrintForgeRowActions extends DebugEntityCommand {
  @Override
  protected void run(Target target) {
    coreSystem.getStream(Aspect.all(Card.class, ForgeRow.class)).forEach(this::printCard);
  }

  private void printCard(int cardEntity) {
    printActions(cardEntity);
  }
}
