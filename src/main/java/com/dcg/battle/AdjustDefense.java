package com.dcg.battle;

import com.dcg.command.CommandArgs;
import java.util.List;

public class AdjustDefense extends UnitEffectBuilder {
  private AdjustDefense(int defense) {
    setIntArgSupplier(() -> defense);
  }

  public static AdjustDefense defense(int defense) {
    return new AdjustDefense(defense);
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    getUnits(targets).forEach(unit -> unit.defense += args.getInt());
  }
}
