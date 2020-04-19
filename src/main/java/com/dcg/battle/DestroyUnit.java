package com.dcg.battle;

import com.dcg.target.Target;

public class DestroyUnit extends UnitEffectBuilder {
  @Override
  protected void run(Target target) {
    target.getTargets().forEach(world::delete);
  }
}
