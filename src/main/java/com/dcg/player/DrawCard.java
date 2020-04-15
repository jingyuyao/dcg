package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.annotations.Wire;
import com.dcg.action.CreateAction;
import com.dcg.card.Card;
import com.dcg.card.PlayCard;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.location.Deck;
import com.dcg.location.Hand;
import com.dcg.location.MoveLocation;
import com.dcg.ownership.OwnershipSystem;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DrawCard extends Command {
  private final int playerEntity;
  @Wire protected CommandChain commandChain;
  @Wire protected Random random;
  protected OwnershipSystem ownershipSystem;

  // TODO: get rid of player entity identifier, update downstream
  public DrawCard(int playerEntity) {
    this.playerEntity = playerEntity;
  }

  @Override
  public void run() {
    List<Integer> deck =
        ownershipSystem
            .getOwnedBy(playerEntity, Aspect.all(Card.class, Deck.class))
            .boxed()
            .collect(Collectors.toList());

    if (deck.size() > 0) {
      int cardEntity = deck.get(random.nextInt(deck.size()));
      commandChain.addStart(
          new MoveLocation(cardEntity, Hand.class),
          new CreateAction(cardEntity, new PlayCard(cardEntity)));
    } else {
      commandChain.addStart(new ReshuffleDiscardPile(playerEntity), new DrawCard(playerEntity));
    }
  }
}
