package com.dcg.battle;

import com.artemis.ComponentMapper;

public class SetFlying extends UnitEffectBuilder {
  private final boolean flying;
  protected ComponentMapper<Unit> mUnit;

  public SetFlying(boolean flying) {
    this.flying = flying;
  }

  @Override
  protected void run() {
    getTargetEntities().forEach(targetEntity -> mUnit.get(targetEntity).flying = flying);
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), flying);
  }
}
