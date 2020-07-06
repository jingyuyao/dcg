package com.dcg.forge;

import static com.dcg.action.CreateAction.action;

import com.artemis.Aspect;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandData;
import com.dcg.location.MercenaryDeck;
import com.dcg.location.ThroneDeck;

public class InitializeBuyCard extends AbstractCommandBuilder {
  @Override
  protected void run(CommandData data) {
    coreSystem
        .getStream(Aspect.one(ThroneDeck.class, MercenaryDeck.class))
        .forEach(
            cardEntity ->
                commandChain.addEnd(action("Buy", new BuyCard()).build(world, cardEntity)));
  }
}
