package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.annotations.Wire;
import com.dcg.action.CreateAction;
import com.dcg.card.Card;
import com.dcg.card.PlayCard;
import com.dcg.command.CommandBase;
import com.dcg.command.CommandChain;
import com.dcg.location.Deck;
import com.dcg.location.DiscardPile;
import com.dcg.location.Hand;
import com.dcg.location.MoveLocation;
import com.dcg.ownership.OwnershipSystem;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DrawCards extends CommandBase {
  private final int numLeft;
  @Wire protected CommandChain commandChain;
  @Wire protected Random random;
  protected OwnershipSystem ownershipSystem;

  public DrawCards(int numLeft) {
    this.numLeft = numLeft;
  }

  @Override
  protected void run() {
    List<Integer> deck =
        ownershipSystem
            .getOwnedBy(owner, Aspect.all(Card.class, Deck.class))
            .boxed()
            .collect(Collectors.toList());
    List<Integer> discardPile =
        ownershipSystem
            .getOwnedBy(owner, Aspect.all(Card.class, DiscardPile.class))
            .boxed()
            .collect(Collectors.toList());

    if (deck.size() > 0) {
      int cardEntity = deck.get(random.nextInt(deck.size()));
      commandChain.addEnd(
          new MoveLocation(Hand.class).build(world, cardEntity),
          new CreateAction(new PlayCard()).build(world, cardEntity));
      if (numLeft > 1) {
        // NOTE: This must come after MoveLocation or else we may draw duplicate cards.
        commandChain.addEnd(new DrawCards(numLeft - 1).build(world, owner));
      }
    } else if (!discardPile.isEmpty()) {
      commandChain.addEnd(new ReshuffleDiscardPile().build(world, owner), build(world, owner));
    } else {
      System.out.println("    Couldn't draw card");
    }
  }

  @Override
  public String toString() {
    return String.format("%s %d", super.toString(), numLeft);
  }
}
