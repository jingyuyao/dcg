package com.dcg.battle;

import com.dcg.command.CommandData;

public class SetLifeSteal extends UnitEffectBuilder {
  private SetLifeSteal(boolean lifeSteal) {
    setBoolArgSupplier(() -> lifeSteal);
  }

  public static SetLifeSteal lifesteal() {
    return new SetLifeSteal(true);
  }

  @Override
  protected String getKeyword() {
    return "Lifesteal";
  }

  @Override
  protected void run(CommandData data) {
    getUnits(data.getTargets()).forEach(unit -> unit.lifeSteal = data.getBool());
  }
}
