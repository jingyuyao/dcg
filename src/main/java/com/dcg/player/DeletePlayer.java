package com.dcg.player;

import java.util.List;

public class DeletePlayer extends PlayerEffect {
  @Override
  protected void run(int originEntity, List<Integer> targets, int value) {
    targets.forEach(world::delete);
  }
}
