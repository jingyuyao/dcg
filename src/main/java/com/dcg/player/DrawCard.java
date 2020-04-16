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
import com.dcg.turn.TurnSystem;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DrawCard extends Command {
  private final int playerEntity;
  @Wire protected CommandChain commandChain;
  @Wire protected Random random;
  protected OwnershipSystem ownershipSystem;
  protected TurnSystem turnSystem;

  public DrawCard() {
    this.playerEntity = -1;
  }

  public DrawCard(int playerEntity) {
    this.playerEntity = playerEntity;
  }

  @Override
  public void run() {
    int playerEntity = this.playerEntity != -1 ? this.playerEntity : turnSystem.getPlayerEntity();
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
    } else if (ownershipSystem
            .getOwnedBy(playerEntity, Aspect.all(Card.class, DiscardPile.class))
            .count()
        > 0) {
      commandChain.addStart(new ReshuffleDiscardPile(playerEntity), new DrawCard(playerEntity));
    } else {
      System.out.println("    Couldn't draw card");
    }
  }
}
