package com.dcg.card;

import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandArgs;
import com.dcg.command.CommandBuilder;
import java.util.List;

public class DeleteCard extends AbstractCommandBuilder {
  private CommandBuilder chained;

  public DeleteCard chain(CommandBuilder builder) {
    this.chained = builder;
    return this;
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    targets.forEach(world::delete);
    if (chained != null) {
      commandChain.addEnd(chained.build(world, originEntity));
    }
  }
}
