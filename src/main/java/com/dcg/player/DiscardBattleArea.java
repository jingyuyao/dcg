package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.annotations.Wire;
import com.dcg.card.Card;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.location.BattleArea;
import com.dcg.location.DiscardPile;
import com.dcg.location.MoveLocation;
import com.dcg.ownership.OwnershipSystem;

public class DiscardBattleArea extends Command {
  private final int playerEntity;
  @Wire CommandChain commandChain;
  OwnershipSystem ownershipSystem;

  public DiscardBattleArea(int playerEntity) {
    this.playerEntity = playerEntity;
  }

  @Override
  public void run() {
    // TODO: also remove defending cards
    Aspect.Builder battleArea = Aspect.all(Card.class, BattleArea.class);
    for (int cardEntity : ownershipSystem.getOwnedBy(battleArea, playerEntity)) {
      commandChain.addStart(new MoveLocation(cardEntity, DiscardPile.class));
    }
  }
}
