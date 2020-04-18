package com.dcg.player;

import com.artemis.Aspect;
import com.dcg.card.Card;
import com.dcg.command.Target;
import com.dcg.location.Deck;
import com.dcg.location.DiscardPile;
import com.dcg.location.MoveLocation;

public class ReshuffleDiscardPile extends PlayerEffect {
  @Override
  protected void run(Target target) {
    target.get().stream()
        .flatMapToInt(
            playerEntity ->
                coreSystem.getChildren(playerEntity, Aspect.all(Card.class, DiscardPile.class)))
        .forEach(
            cardEntity ->
                commandChain.addEnd(new MoveLocation(Deck.class).build(world, cardEntity)));
  }
}
