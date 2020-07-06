package com.dcg.battle;

import com.dcg.command.CommandData;
import java.util.List;

public class SetEndurance extends UnitEffectBuilder {
  private SetEndurance(boolean endurance) {
    setBoolArgSupplier(() -> endurance);
  }

  public static SetEndurance endurance() {
    return new SetEndurance(true);
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandData args) {
    getUnits(targets).forEach(unit -> unit.endurance = args.getBool());
  }
}
