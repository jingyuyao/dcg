package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.annotations.Wire;
import com.dcg.card.Card;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.location.DiscardPile;
import com.dcg.location.MoveLocation;
import com.dcg.location.PlayArea;
import com.dcg.ownership.OwnershipSystem;

public class DiscardPlayArea extends Command {
  @Wire protected CommandChain commandChain;
  protected OwnershipSystem ownershipSystem;

  @Override
  protected void run() {
    ownershipSystem
        .getOwnedBy(owner, Aspect.all(Card.class, PlayArea.class))
        .forEach(
            cardEntity ->
                commandChain.addEnd(
                    new MoveLocation(cardEntity, DiscardPile.class).setOwner(owner)));
  }
}
