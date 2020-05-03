package com.dcg.battle;

import com.dcg.command.CommandArgs;
import java.util.List;

public class SetBerserk extends UnitEffectBuilder {
  private SetBerserk(boolean berserk) {
    setBoolArgSupplier(() -> berserk);
  }

  public static SetBerserk berserk() {
    return new SetBerserk(true);
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    getUnits(targets).forEach(unit -> unit.berserk = args.getBool());
  }
}
