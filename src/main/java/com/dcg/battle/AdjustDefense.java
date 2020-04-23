package com.dcg.battle;

import java.util.List;

public class AdjustDefense extends UnitEffectBuilder {
  public AdjustDefense(int defense) {
    setCommandValue(() -> defense);
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, int value) {
    getUnits(targets).forEach(unit -> unit.defense += value);
  }
}
