package com.dcg.battle;

import java.util.List;

public class SetLifeSteal extends UnitEffectBuilder {
  private final boolean lifeSteal;

  public SetLifeSteal(boolean lifeSteal) {
    this.lifeSteal = lifeSteal;
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, int value) {
    getUnits(targets).forEach(unit -> unit.lifeSteal = lifeSteal);
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), lifeSteal);
  }
}
