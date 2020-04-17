package com.dcg.battle;

public class AdjustStrength extends AdjustUnit {
  private final int strength;

  public AdjustStrength(int strength) {
    this.strength = strength;
  }

  @Override
  protected void run() {
    Unit unit = mUnit.get(getTargetEntity());
    unit.strength += strength;
    if (unit.strength <= 0) {
      // TODO: use command
      world.delete(getTargetEntity());
    }
  }

  @Override
  public String toString() {
    return String.format("%s %d", super.toString(), strength);
  }
}
