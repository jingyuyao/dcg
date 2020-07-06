package com.dcg.battle;

import com.dcg.command.CommandData;

public class SetUnblockable extends UnitEffectBuilder {
  private SetUnblockable(boolean unblockable) {
    setBoolArgSupplier(() -> unblockable);
  }

  public static SetUnblockable unblockable() {
    return new SetUnblockable(true);
  }

  @Override
  protected void run(CommandData data) {
    getUnits(data.getTargets()).forEach(unit -> unit.unblockable = data.getBool());
  }
}
