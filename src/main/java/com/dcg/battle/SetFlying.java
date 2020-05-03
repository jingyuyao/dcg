package com.dcg.battle;

import com.dcg.command.CommandArgs;
import java.util.List;

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
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    getUnits(targets).forEach(unit -> unit.flying = args.getBool());
  }
}
