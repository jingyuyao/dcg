package com.dcg.player;

import com.artemis.Aspect;
import com.dcg.card.Card;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.location.DiscardPile;
import com.dcg.location.MoveLocation;
import com.dcg.location.PlayArea;
import java.util.List;

public class CleanUpStep extends AbstractCommandBuilder {
  @Override
  protected void run(int originEntity, List<Integer> targets, int value) {
    coreSystem
        .getChildren(originEntity, Aspect.all(Card.class, PlayArea.class))
        .forEach(
            cardEntity ->
                commandChain.addEnd(new MoveLocation(DiscardPile.class).build(world, cardEntity)));
    commandChain.addEnd(
        new DrawCards(5).build(world, originEntity),
        new AdvancePlayerStep().build(world, originEntity));
  }
}
