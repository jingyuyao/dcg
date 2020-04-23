package com.dcg.effect;

import com.dcg.game.CoreSystem;
import java.util.function.Supplier;

public class TotalAttackingUnits implements Supplier<Integer> {
  protected CoreSystem coreSystem;

  @Override
  public Integer get() {
    return (int) coreSystem.getAttackingEntities().count();
  }
}
