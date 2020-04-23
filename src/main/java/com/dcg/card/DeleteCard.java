package com.dcg.card;

import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandArgs;
import java.util.List;

public class DeleteCard extends AbstractCommandBuilder {
  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    targets.forEach(world::delete);
  }
}
