package com.dcg.player;

import com.dcg.command.Target;

public class DeletePlayer extends PlayerEffect {
  @Override
  protected void run(Target target) {
    target.get().forEach(world::delete);
  }
}
