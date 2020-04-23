package com.dcg.battle;

import com.dcg.command.CommandArgs;
import java.util.List;

public class SetEndurance extends UnitEffectBuilder {
  public SetEndurance(boolean endurance) {
    setBoolArgSupplier(() -> endurance);
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    getUnits(targets).forEach(unit -> unit.endurance = args.getBool());
  }
}
