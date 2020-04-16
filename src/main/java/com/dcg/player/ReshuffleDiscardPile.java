package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.dcg.card.Card;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.location.Deck;
import com.dcg.location.DiscardPile;
import com.dcg.location.MoveLocation;
import com.dcg.ownership.Owned;
import com.dcg.ownership.OwnershipSystem;

public class ReshuffleDiscardPile extends Command {
  private final int playerEntity;
  @Wire protected CommandChain commandChain;
  protected OwnershipSystem ownershipSystem;
  protected ComponentMapper<Owned> mOwned;

  public ReshuffleDiscardPile(int playerEntity) {
    this.playerEntity = playerEntity;
  }

  @Override
  protected void run() {
    ownershipSystem
        .getOwnedBy(playerEntity, Aspect.all(Card.class, DiscardPile.class))
        .forEach(
            cardEntity -> {
              if (playerEntity == mOwned.get(cardEntity).owner) {
                commandChain.addStart(new MoveLocation(cardEntity, Deck.class));
              }
            });
  }

  @Override
  public String toString() {
    return String.format("%s *%d", super.toString(), playerEntity);
  }
}
