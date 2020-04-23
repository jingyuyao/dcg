package com.dcg.forge;

import com.dcg.card.Cards;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandArgs;
import com.dcg.command.CommandBuilder;
import java.util.List;

public class InitializeForge extends AbstractCommandBuilder {
  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    for (CommandBuilder builder : Cards.createForge()) {
      commandChain.addEnd(builder.build(world, -1));
    }
    commandChain.addEnd(new RefillForgeRow().build(world, -1));
  }
}
