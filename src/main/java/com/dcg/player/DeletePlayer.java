package com.dcg.player;

import com.dcg.target.Target;

public class DeletePlayer extends PlayerEffect {
  @Override
  protected void run(Target target) {
    target.getTargets().forEach(world::delete);
  }
}
