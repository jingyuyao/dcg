package com.dcg.player;

import com.dcg.command.Target;

public class DeletePlayer extends PlayerEffect {
  @Override
  protected void run(Target target) {
    target.getTo().forEach(world::delete);
  }
}
