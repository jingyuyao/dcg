package com.dcg.battle;

import java.util.List;

public class SetBerserk extends UnitEffectBuilder {
  private final boolean berserk;

  public SetBerserk(boolean berserk) {
    this.berserk = berserk;
  }

  @Override
  protected void run(List<Integer> input) {
    getUnits(input).forEach(unit -> unit.berserk = berserk);
  }
}
