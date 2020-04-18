package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.Component;
import com.artemis.annotations.Wire;
import com.dcg.card.Card;
import com.dcg.effect.AbstractEffectBuilder;
import com.dcg.location.Deck;
import com.dcg.location.DiscardPile;
import com.dcg.location.Hand;
import com.dcg.location.MoveLocation;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DrawCards extends AbstractEffectBuilder<Component> {
  private final int numLeft;

  @Wire protected Random random;

  public DrawCards(int numLeft) {
    this.numLeft = numLeft;
  }

  @Override
  protected void run(List<Integer> input) {
    // Ensures this command can be added to both player and card.
    int rootEntity = coreSystem.getRoot(sourceEntity);
    List<Integer> deck =
        coreSystem
            .getChildren(rootEntity, Aspect.all(Card.class, Deck.class))
            .boxed()
            .collect(Collectors.toList());
    List<Integer> discardPile =
        coreSystem
            .getChildren(rootEntity, Aspect.all(Card.class, DiscardPile.class))
            .boxed()
            .collect(Collectors.toList());

    if (deck.size() > 0) {
      int cardEntity = deck.get(random.nextInt(deck.size()));
      commandChain.addEnd(new MoveLocation(Hand.class).build(world, cardEntity));
      if (numLeft > 1) {
        // NOTE: This must come after MoveLocation or else we may draw duplicate cards.
        commandChain.addEnd(new DrawCards(numLeft - 1).build(world, rootEntity));
      }
    } else if (!discardPile.isEmpty()) {
      commandChain.addEnd(
          new ReshuffleDiscardPile().build(world, rootEntity), build(world, rootEntity));
    } else {
      System.out.println("    Couldn't draw card");
    }
  }

  @Override
  public String toString() {
    return String.format("%s %d", super.toString(), numLeft);
  }
}
