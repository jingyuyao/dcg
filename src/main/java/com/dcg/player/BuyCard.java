package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.annotations.Wire;
import com.dcg.card.Card;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.location.Deck;
import com.dcg.location.ForgeRow;
import com.dcg.location.MoveLocation;
import com.dcg.ownership.Own;

public class BuyCard extends Command {
  private final int playerEntity;
  @Wire CommandChain commandChain;
  AspectSubscriptionManager manager;

  public BuyCard(int playerEntity) {
    this.playerEntity = playerEntity;
  }

  @Override
  public void run() {
    int cardEntity = manager.get(Aspect.all(Card.class, ForgeRow.class)).getEntities().get(0);
    commandChain.addStart(
        new Own(playerEntity, cardEntity), new MoveLocation(cardEntity, Deck.class));
  }
}
