package com.dcg.battle;

public class SetLifeSteal extends UnitEffectBuilder {
  private final boolean lifeSteal;

  public SetLifeSteal(boolean lifeSteal) {
    this.lifeSteal = lifeSteal;
  }

  @Override
  protected void run() {
    getTargetComponents().forEach(unit -> unit.lifeSteal = lifeSteal);
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), lifeSteal);
  }
}
