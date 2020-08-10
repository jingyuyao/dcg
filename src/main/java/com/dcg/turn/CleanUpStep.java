package com.dcg.turn;

import static com.dcg.player.DrawCards.autoDraw;

import com.artemis.Aspect;
import com.dcg.card.Card;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandData;
import com.dcg.location.DiscardPile;
import com.dcg.location.MoveLocation;
import com.dcg.location.PlayArea;

public class CleanUpStep extends AbstractCommandBuilder {
  @Override
  protected void run(CommandData data) {
    int originEntity = data.getOriginEntity();
    coreSystem
        .getChildren(originEntity, Aspect.all(Card.class, PlayArea.class))
        .forEach(
            cardEntity ->
                commandChain.addEnd(new MoveLocation(DiscardPile.class).build(world, cardEntity)));
    commandChain.addEnd(
        autoDraw(5).build(world, originEntity), new AdvancePlayerStep().build(world, originEntity));
  }
}
