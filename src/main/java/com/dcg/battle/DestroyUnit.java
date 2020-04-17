package com.dcg.battle;

public class DestroyUnit extends UnitEffectBuilder {
  @Override
  protected void run() {
    getTargetEntities().forEach(world::delete);
  }
}
