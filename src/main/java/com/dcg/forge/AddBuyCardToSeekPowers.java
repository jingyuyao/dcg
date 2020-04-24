package com.dcg.forge;

import com.artemis.Aspect;
import com.dcg.action.CreateAction;
import com.dcg.card.SeekPower;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandArgs;
import java.util.List;

public class AddBuyCardToSeekPowers extends AbstractCommandBuilder {
  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    coreSystem
        .getStream(Aspect.all(SeekPower.class))
        .forEach(
            cardEntity ->
                commandChain.addEnd(new CreateAction(new BuyCard()).build(world, cardEntity)));
  }
}
