package com.dcg.card;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandBuilder;
import com.dcg.command.CommandData;
import com.dcg.forge.DrawForgeCards;
import com.dcg.location.ForgeRow;
import java.util.List;

public class DeleteCard extends AbstractCommandBuilder {
  private CommandBuilder chained;
  protected ComponentMapper<ForgeRow> mForgeRow;

  public DeleteCard chain(CommandBuilder builder) {
    this.chained = builder;
    return this;
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandData args) {
    for (int cardEntity : targets) {
      if (mForgeRow.has(cardEntity)) {
        commandChain.addEnd(new DrawForgeCards(1).build(world, -1));
      }
      world.delete(cardEntity);
    }
    if (chained != null) {
      commandChain.addEnd(chained.build(world, originEntity));
    }
  }
}
