package com.dcg.player;

import com.dcg.command.CommandArgs;
import java.util.List;

public class DeletePlayer extends PlayerEffect {
  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    targets.forEach(world::delete);
  }
}
