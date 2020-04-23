package com.dcg.battle;

import java.util.List;

public class DestroyUnit extends UnitEffectBuilder {
  @Override
  protected void run(int originEntity, List<Integer> targets, int value) {
    targets.forEach(world::delete);
  }
}
