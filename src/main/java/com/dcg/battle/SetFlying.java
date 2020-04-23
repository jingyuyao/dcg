package com.dcg.battle;

import com.dcg.command.CommandArgs;
import java.util.List;

public class SetFlying extends UnitEffectBuilder {
  public SetFlying(boolean flying) {
    setBoolArgSupplier(() -> flying);
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    getUnits(targets).forEach(unit -> unit.flying = args.getBool());
  }
}
