package com.dcg.battle;

import com.dcg.command.CommandData;

public class SetFlying extends UnitEffectBuilder {
  private SetFlying(boolean flying) {
    setBoolArgSupplier(() -> flying);
  }

  public static SetFlying flying() {
    return new SetFlying(true);
  }

  public static SetFlying removeFlying() {
    return new SetFlying(false);
  }

  @Override
  protected String getKeyword() {
    return "Flying";
  }

  @Override
  protected void run(CommandData data) {
    getUnits(data.getTargets()).forEach(unit -> unit.flying = data.getBool());
  }
}
