package com.dcg.player;

import com.artemis.Aspect;
import com.dcg.card.Card;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.location.Deck;
import com.dcg.location.DiscardPile;
import com.dcg.location.MoveLocation;

public class ReshuffleDiscardPile extends AbstractCommandBuilder {;

  @Override
  protected void run() {
    coreSystem
        .getChildren(sourceEntity, Aspect.all(Card.class, DiscardPile.class))
        .forEach(
            cardEntity ->
                commandChain.addEnd(new MoveLocation(Deck.class).build(world, cardEntity)));
  }
}
