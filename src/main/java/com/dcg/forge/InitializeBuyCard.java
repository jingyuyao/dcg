package com.dcg.forge;

import static com.dcg.action.CreateAction.action;

import com.artemis.Aspect;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandData;
import com.dcg.location.MercenaryDeck;
import com.dcg.location.ThroneDeck;
import java.util.List;

public class InitializeBuyCard extends AbstractCommandBuilder {
  @Override
  protected void run(int originEntity, List<Integer> targets, CommandData args) {
    coreSystem
        .getStream(Aspect.one(ThroneDeck.class, MercenaryDeck.class))
        .forEach(
            cardEntity ->
                commandChain.addEnd(action("Buy", new BuyCard()).build(world, cardEntity)));
  }
}
