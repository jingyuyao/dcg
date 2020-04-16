package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.annotations.Wire;
import com.dcg.action.CreateAction;
import com.dcg.card.Card;
import com.dcg.card.PlayCard;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.location.Deck;
import com.dcg.location.DiscardPile;
import com.dcg.location.Hand;
import com.dcg.location.MoveLocation;
import com.dcg.ownership.OwnershipSystem;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DrawCard extends Command {
  @Wire protected CommandChain commandChain;
  @Wire protected Random random;
  protected OwnershipSystem ownershipSystem;

  @Override
  protected void run() {
    List<Integer> deck =
        ownershipSystem
            .getOwnedBy(owner, Aspect.all(Card.class, Deck.class))
            .boxed()
            .collect(Collectors.toList());

    if (deck.size() > 0) {
      int cardEntity = deck.get(random.nextInt(deck.size()));
      commandChain.addEnd(
          new MoveLocation(cardEntity, Hand.class).setOwner(owner),
          new CreateAction(new PlayCard(cardEntity).setOwner(cardEntity)).setOwner(cardEntity));
    } else if (ownershipSystem.getOwnedBy(owner, Aspect.all(Card.class, DiscardPile.class)).count()
        > 0) {
      commandChain.addEnd(
          new ReshuffleDiscardPile().setOwner(owner), new DrawCard().setOwner(owner));
    } else {
      System.out.println("    Couldn't draw card");
    }
  }
}
