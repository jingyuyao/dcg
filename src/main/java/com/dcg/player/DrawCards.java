package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.annotations.Wire;
import com.dcg.card.Card;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.location.Deck;
import com.dcg.location.DiscardPile;
import com.dcg.location.Hand;
import com.dcg.location.MoveLocation;
import com.dcg.targetsource.SourceEntityRoot;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DrawCards extends AbstractCommandBuilder {
  private final int numLeft;

  @Wire protected Random random;

  public DrawCards(int numLeft) {
    this.numLeft = numLeft;
    setTargetSource(new SourceEntityRoot());
    addTargetConditions(input -> getDeck(input).count() > 0 || getDiscardPile(input).count() > 0);
  }

  @Override
  protected void run(List<Integer> input) {
    getTargetEntities(input)
        .forEach(
            entity -> {
              List<Integer> deck = getDeck(input).boxed().collect(Collectors.toList());

              if (deck.size() > 0) {
                int cardEntity = deck.get(random.nextInt(deck.size()));
                commandChain.addEnd(new MoveLocation(Hand.class).build(world, cardEntity));
                if (numLeft > 1) {
                  // NOTE: This must come after MoveLocation or else we may draw duplicate cards.
                  commandChain.addEnd(new DrawCards(numLeft - 1).build(world, entity));
                }
              } else {
                commandChain.addEnd(
                    new ReshuffleDiscardPile().build(world, entity), build(world, entity));
              }
            });
  }

  private IntStream getDeck(List<Integer> input) {
    return getTargetEntities(input).stream()
        .flatMapToInt(entity -> coreSystem.getChildren(entity, Aspect.all(Card.class, Deck.class)));
  }

  private IntStream getDiscardPile(List<Integer> input) {
    return getTargetEntities(input).stream()
        .flatMapToInt(
            entity -> coreSystem.getChildren(entity, Aspect.all(Card.class, DiscardPile.class)));
  }

  @Override
  public String toString() {
    return String.format("%s %d", super.toString(), numLeft);
  }
}
