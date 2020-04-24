package com.dcg.commandargs;

import com.dcg.game.CoreSystem;
import java.util.function.Supplier;

public class TotalAttackingUnits implements Supplier<Integer> {
  protected CoreSystem coreSystem;

  @Override
  public Integer get() {
    return (int) coreSystem.getAttackingEntities().count();
  }
}
