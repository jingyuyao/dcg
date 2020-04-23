package com.dcg.battle;

import java.util.List;

public class SetEndurance extends UnitEffectBuilder {
  private final boolean endurance;

  public SetEndurance(boolean endurance) {
    this.endurance = endurance;
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, int value) {
    getUnits(targets).forEach(unit -> unit.endurance = endurance);
  }
}
