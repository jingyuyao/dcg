package com.dcg.battle;

import com.dcg.command.CommandArgs;
import java.util.List;

public class SetUnblockable extends UnitEffectBuilder {
  private SetUnblockable(boolean unblockable) {
    setBoolArgSupplier(() -> unblockable);
  }

  public static SetUnblockable unblockable() {
    return new SetUnblockable(true);
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    getUnits(targets).forEach(unit -> unit.unblockable = args.getBool());
  }
}
