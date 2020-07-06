package com.dcg.battle;

import com.dcg.command.CommandData;
import java.util.List;

public class SetLifeSteal extends UnitEffectBuilder {
  private SetLifeSteal(boolean lifeSteal) {
    setBoolArgSupplier(() -> lifeSteal);
  }

  public static SetLifeSteal lifesteal() {
    return new SetLifeSteal(true);
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandData args) {
    getUnits(targets).forEach(unit -> unit.lifeSteal = args.getBool());
  }
}
