package com.dcg.player;

import java.util.List;

public class DeletePlayer extends PlayerEffect {
  @Override
  protected void run(List<Integer> input) {
    getTargetEntities(input).forEach(world::delete);
  }
}
