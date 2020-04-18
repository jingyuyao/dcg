package com.dcg.battle;

import java.util.List;

public class AdjustStrength extends UnitEffectBuilder {
  private final int strength;

  public AdjustStrength(int strength) {
    this.strength = strength;
  }

  @Override
  protected void run(List<Integer> input) {
    getTargetEntities(input)
        .forEach(
            targetEntity -> {
              Unit unit = mUnit.get(targetEntity);
              unit.strength += strength;
              if (unit.strength <= 0) {
                commandChain.addEnd(new DestroyUnit().build(world, targetEntity));
              }
            });
  }

  @Override
  public String toString() {
    return String.format("%s %d", super.toString(), strength);
  }
}
