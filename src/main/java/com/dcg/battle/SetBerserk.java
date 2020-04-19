package com.dcg.battle;

import com.dcg.target.Target;

public class SetBerserk extends UnitEffectBuilder {
  private final boolean berserk;

  public SetBerserk(boolean berserk) {
    this.berserk = berserk;
  }

  @Override
  protected void run(Target target) {
    getUnits(target).forEach(unit -> unit.berserk = berserk);
  }
}
