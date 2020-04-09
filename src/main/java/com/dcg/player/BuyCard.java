package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.dcg.card.Card;
import com.dcg.card.Deck;
import com.dcg.card.ForgeRow;
import com.dcg.card.MoveLocation;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;

public class BuyCard extends Command {
  private final int playerEntity;
  @Wire CommandChain commandChain;
  AspectSubscriptionManager manager;
  ComponentMapper<PlayerOwned> mPlayerOwned;

  public BuyCard(int playerEntity) {
    this.playerEntity = playerEntity;
  }

  @Override
  public void run() {
    int cardEntity = manager.get(Aspect.all(Card.class, ForgeRow.class)).getEntities().get(0);
    // TODO: make this a command
    mPlayerOwned.create(cardEntity).playerEntity = playerEntity;
    commandChain.addStart(new MoveLocation(cardEntity, Deck.class));
  }
}
