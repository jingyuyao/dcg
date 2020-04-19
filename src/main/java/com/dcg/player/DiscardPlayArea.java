package com.dcg.player;

import com.artemis.Aspect;
import com.dcg.card.Card;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.Target;
import com.dcg.location.DiscardPile;
import com.dcg.location.MoveLocation;
import com.dcg.location.PlayArea;

public class DiscardPlayArea extends AbstractCommandBuilder {
  @Override
  protected void run(Target target) {
    coreSystem
        .getChildren(target.getFrom(), Aspect.all(Card.class, PlayArea.class))
        .forEach(
            cardEntity ->
                commandChain.addEnd(new MoveLocation(DiscardPile.class).build(world, cardEntity)));
  }
}
