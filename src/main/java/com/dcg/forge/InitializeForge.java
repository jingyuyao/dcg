package com.dcg.forge;

import com.dcg.card.Cards;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandBuilder;
import java.util.List;

public class InitializeForge extends AbstractCommandBuilder {

  @Override
  protected void run(List<Integer> input) {
    for (CommandBuilder builder : Cards.FORGE_CARDS) {
      commandChain.addEnd(builder.build(world, -1));
    }
    commandChain.addEnd(new RefillForgeRow().build(world, -1));
  }
}
