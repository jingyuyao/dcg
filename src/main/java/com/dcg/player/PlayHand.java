package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.annotations.Wire;
import com.dcg.card.Card;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.location.Hand;
import com.dcg.location.MoveLocation;
import com.dcg.location.PlayArea;
import com.dcg.ownership.OwnershipSystem;
import java.util.List;

public class PlayHand extends Command {
  private final int playerEntity;
  @Wire CommandChain commandChain;
  protected OwnershipSystem ownershipSystem;

  public PlayHand(int playerEntity) {
    this.playerEntity = playerEntity;
  }

  @Override
  public void run() {
    List<Integer> hand =
        ownershipSystem.getOwnedBy(playerEntity, Aspect.all(Card.class, Hand.class));
    for (int cardEntity : hand) {
      commandChain.addStart(new MoveLocation(cardEntity, PlayArea.class));
    }
  }
}
