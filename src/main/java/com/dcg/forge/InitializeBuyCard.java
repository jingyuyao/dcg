package com.dcg.forge;

import com.artemis.Aspect;
import com.dcg.action.CreateAction;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandArgs;
import com.dcg.location.MercenaryDeck;
import com.dcg.location.ThroneDeck;
import java.util.List;

public class InitializeBuyCard extends AbstractCommandBuilder {
  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    coreSystem
        .getStream(Aspect.one(ThroneDeck.class, MercenaryDeck.class))
        .forEach(
            cardEntity ->
                commandChain.addEnd(new CreateAction(new BuyCard()).build(world, cardEntity)));
  }
}
