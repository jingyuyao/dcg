package com.dcg.battle;

import com.dcg.command.Target;

public class AdjustStrength extends UnitEffectBuilder {
  private final int strength;

  public AdjustStrength(int strength) {
    this.strength = strength;
  }

  @Override
  protected void run(Target target) {
    for (int targetEntity : target.getTo()) {
      Unit unit = mUnit.get(targetEntity);
      unit.strength += strength;
      if (unit.strength <= 0) {
        commandChain.addEnd(new DestroyUnit().build(world, targetEntity));
      }
    }
  }

  @Override
  public String toString() {
    return String.format("%s %d", super.toString(), strength);
  }
}
