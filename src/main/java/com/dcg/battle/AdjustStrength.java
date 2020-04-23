package com.dcg.battle;

import com.dcg.command.CommandArgs;
import java.util.List;

public class AdjustStrength extends UnitEffectBuilder {
  public AdjustStrength(int strength) {
    setIntArgSupplier(() -> strength);
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    for (int targetEntity : targets) {
      Unit unit = mUnit.get(targetEntity);
      unit.strength += args.getInt();
      if (unit.strength <= 0) {
        commandChain.addEnd(new DestroyUnit().build(world, targetEntity));
      }
    }
  }
}
