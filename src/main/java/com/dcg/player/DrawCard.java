package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.dcg.card.Card;
import com.dcg.card.Deck;
import com.dcg.card.Hand;
import com.dcg.card.MoveLocation;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;

public class DrawCard implements Command {
  private final int playerEntity;
  @Wire CommandChain commandChain;
  PlayerOwnedSystem playerOwnedSystem;
  ComponentMapper<Player> mPlayer;

  public DrawCard(int playerEntity) {
    this.playerEntity = playerEntity;
  }

  @Override
  public void run() {
    Aspect.Builder deck = Aspect.all(Card.class, PlayerOwned.class, Deck.class);

    for (int cardEntity : playerOwnedSystem.filter(deck, playerEntity)) {
      commandChain.addStart(new MoveLocation(cardEntity, Hand.class));
      return;
    }

    commandChain.addStart(new ReshuffleDiscardPile(playerEntity), new DrawCard(playerEntity));
  }

  @Override
  public String toString() {
    return "TryDrawCard " + mPlayer.get(playerEntity);
  }
}
