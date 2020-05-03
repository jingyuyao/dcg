package com.dcg.battle;

import com.dcg.command.CommandArgs;
import java.util.List;
import java.util.function.Supplier;

public class AdjustStrength extends UnitEffectBuilder {
  private AdjustStrength(Supplier<Integer> supplier) {
    setIntArgSupplier(supplier);
  }

  public static AdjustStrength strength(int strength) {
    return new AdjustStrength(() -> strength);
  }

  public static AdjustStrength strength(Supplier<Integer> supplier) {
    return new AdjustStrength(supplier);
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
