package com.dcg.player;

import com.artemis.Aspect;
import com.dcg.card.Card;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.location.DiscardPile;
import com.dcg.location.MoveLocation;
import com.dcg.location.PlayArea;
import java.util.List;

public class DiscardPlayArea extends AbstractCommandBuilder {;

  @Override
  protected void run(List<Integer> input) {
    coreSystem
        .getChildren(sourceEntity, Aspect.all(Card.class, PlayArea.class))
        .forEach(
            cardEntity ->
                commandChain.addEnd(new MoveLocation(DiscardPile.class).build(world, cardEntity)));
  }
}
