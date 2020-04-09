package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.dcg.card.Card;
import com.dcg.card.Deck;
import com.dcg.card.DiscardPile;
import com.dcg.card.MoveLocation;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;

public class ReshuffleDiscardPile implements Command {

  private final int playerEntity;
  @Wire CommandChain commandChain;
  PlayerOwnedSystem playerOwnedSystem;
  ComponentMapper<Player> mPlayer;
  ComponentMapper<PlayerOwned> mPlayerOwned;

  public ReshuffleDiscardPile(int playerEntity) {
    this.playerEntity = playerEntity;
  }

  @Override
  public void run() {
    Aspect.Builder discardPile = Aspect.all(Card.class, PlayerOwned.class, DiscardPile.class);

    for (int cardEntity : playerOwnedSystem.filter(discardPile, playerEntity)) {
      if (playerEntity == mPlayerOwned.get(cardEntity).playerEntity) {
        commandChain.addStart(new MoveLocation(cardEntity, Deck.class));
      }
    }
  }

  @Override
  public String toString() {
    return "ReshuffleDiscardPile " + mPlayer.get(playerEntity);
  }
}
