package com.dcg.battle;

import com.dcg.command.CommandData;
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
  protected String getKeyword() {
    return "Strength";
  }

  @Override
  protected void run(CommandData data) {
    for (int targetEntity : data.getTargets()) {
      Unit unit = mUnit.get(targetEntity);
      unit.strength += data.getInt();
      if (unit.strength <= 0) {
        commandChain.addEnd(new DestroyUnit().build(world, targetEntity));
      }
    }
  }
}
