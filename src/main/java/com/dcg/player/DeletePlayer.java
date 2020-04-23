package com.dcg.player;

import java.util.List;

public class DeletePlayer extends PlayerEffect {
  @Override
  protected void run(int originEntity, List<Integer> targets) {
    targets.forEach(world::delete);
  }
}
