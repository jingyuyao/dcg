package com.dcg.battle;

import com.dcg.command.Target;

public class SetEndurance extends UnitEffectBuilder {
  private final boolean endurance;

  public SetEndurance(boolean endurance) {
    this.endurance = endurance;
  }

  @Override
  protected void run(Target target) {
    getUnits(target).forEach(unit -> unit.endurance = endurance);
  }
}
