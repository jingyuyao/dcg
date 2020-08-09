package com.dcg.battle;

import com.dcg.command.CommandData;

public class SetEndurance extends UnitEffectBuilder {
  private SetEndurance(boolean endurance) {
    setBoolArgSupplier(() -> endurance);
  }

  public static SetEndurance endurance() {
    return new SetEndurance(true);
  }

  @Override
  protected String getKeyword() {
    return "Endurance";
  }

  @Override
  protected void run(CommandData data) {
    getUnits(data.getTargets()).forEach(unit -> unit.endurance = data.getBool());
  }
}
