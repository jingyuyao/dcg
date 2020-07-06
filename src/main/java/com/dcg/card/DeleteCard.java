package com.dcg.card;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandBuilder;
import com.dcg.command.CommandData;
import com.dcg.forge.DrawForgeCards;
import com.dcg.location.ForgeRow;

public class DeleteCard extends AbstractCommandBuilder {
  private CommandBuilder chained;
  protected ComponentMapper<ForgeRow> mForgeRow;

  public DeleteCard chain(CommandBuilder builder) {
    this.chained = builder;
    return this;
  }

  @Override
  protected void run(CommandData data) {
    for (int cardEntity : data.getTargets()) {
      if (mForgeRow.has(cardEntity)) {
        commandChain.addEnd(new DrawForgeCards(1).build(world, -1));
      }
      world.delete(cardEntity);
    }
    if (chained != null) {
      commandChain.addEnd(chained.build(world, data.getOriginEntity()));
    }
  }
}
