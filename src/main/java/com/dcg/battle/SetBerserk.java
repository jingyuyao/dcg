package com.dcg.battle;

import com.dcg.command.CommandData;

public class SetBerserk extends UnitEffectBuilder {
  private SetBerserk(boolean berserk) {
    setBoolArgSupplier(() -> berserk);
  }

  public static SetBerserk berserk() {
    return new SetBerserk(true);
  }

  @Override
  protected void run(CommandData data) {
    getUnits(data.getTargets()).forEach(unit -> unit.berserk = data.getBool());
  }
}
