package com.dcg.forge;

import com.dcg.card.Cards;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandBuilder;
import com.dcg.target.Target;

public class InitializeForge extends AbstractCommandBuilder {
  @Override
  protected void run(Target target) {
    for (CommandBuilder builder : Cards.createForge()) {
      commandChain.addEnd(builder.build(world, -1));
    }
    commandChain.addEnd(new RefillForgeRow().build(world, -1));
  }
}
