package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.dcg.card.BuyPile;
import com.dcg.card.Card;
import com.dcg.card.DrawPile;
import com.dcg.card.MoveLocation;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.turn.Turn;

public class BuyCard implements Command {
  @Wire CommandChain commandChain;
  AspectSubscriptionManager manager;
  ComponentMapper<PlayerOwned> mPlayerOwned;

  @Override
  public void run() {
    int playerEntity = manager.get(Aspect.all(Player.class, Turn.class)).getEntities().get(0);
    int cardEntity = manager.get(Aspect.all(Card.class, BuyPile.class)).getEntities().get(0);
    mPlayerOwned.create(cardEntity).playerEntity = playerEntity;
    commandChain.addStart(new MoveLocation(cardEntity, DrawPile.class));
  }

  @Override
  public String toString() {
    return "BuyCard";
  }
}
