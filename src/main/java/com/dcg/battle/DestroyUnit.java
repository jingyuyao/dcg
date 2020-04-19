package com.dcg.battle;

import com.dcg.command.Target;

public class DestroyUnit extends UnitEffectBuilder {
  @Override
  protected void run(Target target) {
    target.getTo().forEach(world::delete);
  }
}
