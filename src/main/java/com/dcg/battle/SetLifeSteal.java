package com.dcg.battle;

import com.dcg.command.Target;

public class SetLifeSteal extends UnitEffectBuilder {
  private final boolean lifeSteal;

  public SetLifeSteal(boolean lifeSteal) {
    this.lifeSteal = lifeSteal;
  }

  @Override
  protected void run(Target target) {
    getUnits(target).forEach(unit -> unit.lifeSteal = lifeSteal);
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), lifeSteal);
  }
}
