package com.dcg.player;

import com.artemis.Aspect;
import com.dcg.card.Card;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.location.DiscardPile;
import com.dcg.location.MoveLocation;
import com.dcg.location.PlayArea;
import com.dcg.target.Target;

public class DiscardPlayArea extends AbstractCommandBuilder {
  @Override
  protected void run(Target target) {
    coreSystem
        .getChildren(target.getOrigin(), Aspect.all(Card.class, PlayArea.class))
        .forEach(
            cardEntity ->
                commandChain.addEnd(new MoveLocation(DiscardPile.class).build(world, cardEntity)));
  }
}
