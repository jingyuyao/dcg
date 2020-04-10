package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.dcg.card.Card;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.location.Deck;
import com.dcg.location.Hand;
import com.dcg.location.MoveLocation;
import com.dcg.ownership.OwnershipSystem;

public class DrawCard extends Command {
  private final int playerEntity;
  @Wire CommandChain commandChain;
  OwnershipSystem ownershipSystem;
  ComponentMapper<Player> mPlayer;

  public DrawCard(int playerEntity) {
    this.playerEntity = playerEntity;
  }

  @Override
  public void run() {
    Aspect.Builder deck = Aspect.all(Card.class, Deck.class);

    for (int cardEntity : ownershipSystem.getOwnedBy(deck, playerEntity)) {
      commandChain.addStart(new MoveLocation(cardEntity, Hand.class));
      return;
    }

    commandChain.addStart(new ReshuffleDiscardPile(playerEntity), new DrawCard(playerEntity));
  }

  @Override
  public String toString() {
    return super.toString() + mPlayer.get(playerEntity);
  }
}
