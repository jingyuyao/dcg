package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.annotations.Wire;
import com.dcg.card.Card;
import com.dcg.location.Deck;
import com.dcg.location.DiscardPile;
import com.dcg.location.Hand;
import com.dcg.location.MoveLocation;
import com.dcg.target.Target;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.mostlyoriginal.api.utils.Preconditions;

public class DrawCards extends PlayerEffect {
  private final int numLeft;
  @Wire protected Random random;

  public DrawCards(int numLeft) {
    this.numLeft = numLeft;
    addTargetConditions(
        target ->
            Preconditions.checkArgument(
                target.getTargets().stream()
                    .allMatch(
                        playerEntity ->
                            getDeck(playerEntity).count() > 0
                                || getDiscardPile(playerEntity).count() > 0),
                "Must have cards in deck or discard pile to draw"));
  }

  @Override
  protected void run(Target target) {
    for (int playerEntity : target.getTargets()) {
      List<Integer> deck = getDeck(playerEntity).collect(Collectors.toList());

      if (deck.size() > 0) {
        int cardEntity = deck.get(random.nextInt(deck.size()));
        commandChain.addEnd(new MoveLocation(Hand.class).build(world, cardEntity));
        if (numLeft > 1) {
          // NOTE: This must come after MoveLocation or else we may draw duplicate cards.
          commandChain.addEnd(new DrawCards(numLeft - 1).build(world, playerEntity));
        }
      } else {
        commandChain.addEnd(
            new ReshuffleDiscardPile().build(world, playerEntity), build(world, playerEntity));
      }
    }
  }

  private Stream<Integer> getDeck(int playerEntity) {
    return coreSystem.getChildren(playerEntity, Aspect.all(Card.class, Deck.class));
  }

  private Stream<Integer> getDiscardPile(int playerEntity) {
    return coreSystem.getChildren(playerEntity, Aspect.all(Card.class, DiscardPile.class));
  }

  @Override
  public String toString() {
    return String.format("%s %d", super.toString(), numLeft);
  }
}
