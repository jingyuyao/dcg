package com.dcg.battle;

import java.util.List;

public class DestroyUnit extends UnitEffectBuilder {
  @Override
  protected void run(List<Integer> input) {
    getTargetEntities(input).forEach(world::delete);
  }
}
