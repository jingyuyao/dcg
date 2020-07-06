package com.dcg.battle;

import com.dcg.command.CommandData;

public class AdjustDefense extends UnitEffectBuilder {
  private AdjustDefense(int defense) {
    setIntArgSupplier(() -> defense);
  }

  public static AdjustDefense defense(int defense) {
    return new AdjustDefense(defense);
  }

  @Override
  protected void run(CommandData data) {
    getUnits(data.getTargets()).forEach(unit -> unit.defense += data.getInt());
  }
}
