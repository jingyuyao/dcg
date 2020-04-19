package com.dcg.effect;

import com.dcg.game.CoreSystem;

public class TotalAttackingUnits implements EffectValueSupplier {
  protected CoreSystem coreSystem;

  @Override
  public Integer get() {
    return (int) coreSystem.getAttackingEntities().count();
  }
}
