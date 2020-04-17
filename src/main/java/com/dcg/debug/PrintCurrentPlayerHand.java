package com.dcg.debug;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.card.Card;
import com.dcg.game.CoreSystem;
import com.dcg.location.Hand;
import com.dcg.player.Turn;

public class PrintCurrentPlayerHand extends DebugEntityCommand {
  protected CoreSystem coreSystem;
  protected ComponentMapper<Card> mCard;

  @Override
  protected void run() {
    coreSystem
        .getStream(Aspect.all(Turn.class))
        .flatMap(
            playerEntity ->
                coreSystem.getOwnedBy(playerEntity, Aspect.all(Card.class, Hand.class)))
        .forEach(this::printCard);
  }

  private void printCard(int cardEntity) {
    System.out.printf("    *%d %s\n", cardEntity, mCard.get(cardEntity));
    printActions(cardEntity);
  }
}
