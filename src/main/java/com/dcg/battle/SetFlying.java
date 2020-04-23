package com.dcg.battle;

import java.util.List;

public class SetFlying extends UnitEffectBuilder {
  private final boolean flying;

  public SetFlying(boolean flying) {
    this.flying = flying;
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, int value) {
    getUnits(targets).forEach(unit -> unit.flying = flying);
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), flying);
  }
}
