package com.dcg.battle;

import com.dcg.command.CommandArgs;
import java.util.List;

public class SetLifeSteal extends UnitEffectBuilder {
  private SetLifeSteal(boolean lifeSteal) {
    setBoolArgSupplier(() -> lifeSteal);
  }

  public static SetLifeSteal lifeSteal() {
    return new SetLifeSteal(true);
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    getUnits(targets).forEach(unit -> unit.lifeSteal = args.getBool());
  }
}
