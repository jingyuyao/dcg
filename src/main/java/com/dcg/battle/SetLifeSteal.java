package com.dcg.battle;

public class SetLifeSteal extends AdjustUnit {
  private final boolean lifeSteal;

  public SetLifeSteal(boolean lifeSteal) {
    this.lifeSteal = lifeSteal;
  }

  @Override
  protected void run() {
    mUnit.get(getTargetEntity()).lifeSteal = lifeSteal;
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), lifeSteal);
  }
}
