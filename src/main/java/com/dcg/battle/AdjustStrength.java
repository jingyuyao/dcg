package com.dcg.battle;

import java.util.List;

public class AdjustStrength extends UnitEffectBuilder {
  public AdjustStrength(int strength) {
    setCommandValue(() -> strength);
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, int value) {
    for (int targetEntity : targets) {
      Unit unit = mUnit.get(targetEntity);
      unit.strength += value;
      if (unit.strength <= 0) {
        commandChain.addEnd(new DestroyUnit().build(world, targetEntity));
      }
    }
  }
}
