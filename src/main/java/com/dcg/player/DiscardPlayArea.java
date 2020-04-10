package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.dcg.card.Card;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.location.DiscardPile;
import com.dcg.location.MoveLocation;
import com.dcg.location.PlayArea;
import com.dcg.ownership.Owned;
import com.dcg.ownership.OwnershipSystem;

public class DiscardPlayArea extends Command {
  private final int playerEntity;
  @Wire CommandChain commandChain;
  OwnershipSystem ownershipSystem;
  ComponentMapper<Player> mPlayer;

  public DiscardPlayArea(int playerEntity) {
    this.playerEntity = playerEntity;
  }

  @Override
  public void run() {
    Aspect.Builder playArea = Aspect.all(Card.class, Owned.class, PlayArea.class);
    for (int cardEntity : ownershipSystem.filter(playArea, playerEntity)) {
      commandChain.addStart(new MoveLocation(cardEntity, DiscardPile.class));
    }
  }

  @Override
  public String toString() {
    return super.toString() + mPlayer.get(playerEntity);
  }
}
