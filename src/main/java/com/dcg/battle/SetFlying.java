package com.dcg.battle;

import com.dcg.command.Target;

public class SetFlying extends UnitEffectBuilder {
  private final boolean flying;

  public SetFlying(boolean flying) {
    this.flying = flying;
  }

  @Override
  protected void run(Target target) {
    getUnits(target).forEach(unit -> unit.flying = flying);
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), flying);
  }
}
