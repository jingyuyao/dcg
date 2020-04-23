package com.dcg.card;

import com.dcg.command.AbstractCommandBuilder;
import java.util.List;

public class DeleteCard extends AbstractCommandBuilder {
  @Override
  protected void run(int originEntity, List<Integer> targets, int value) {
    targets.forEach(world::delete);
  }
}
