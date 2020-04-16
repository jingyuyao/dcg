package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.annotations.Wire;
import com.dcg.action.DeleteActions;
import com.dcg.card.Card;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.location.DiscardPile;
import com.dcg.location.MoveLocation;
import com.dcg.location.PlayArea;
import com.dcg.ownership.OwnershipSystem;

public class DiscardPlayArea extends Command {
  private final int playerEntity;
  @Wire protected CommandChain commandChain;
  protected OwnershipSystem ownershipSystem;

  public DiscardPlayArea(int playerEntity) {
    this.playerEntity = playerEntity;
  }

  @Override
  protected void run() {
    ownershipSystem
        .getOwnedBy(playerEntity, Aspect.all(Card.class, PlayArea.class))
        .forEach(
            cardEntity ->
                commandChain.addEnd(
                    new MoveLocation(cardEntity, DiscardPile.class),
                    new DeleteActions(cardEntity)));
  }

  @Override
  public String toString() {
    return String.format("%s *%d", super.toString(), playerEntity);
  }
}
