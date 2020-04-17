package com.dcg.battle;

import com.artemis.ComponentMapper;

public class AdjustDefense extends UnitEffectBuilder {
  protected ComponentMapper<Unit> mUnit;
  private final int defense;

  public AdjustDefense(int defense) {
    this.defense = defense;
  }

  @Override
  protected void run() {
    getTargetEntities().forEach(targetEntity -> mUnit.get(targetEntity).defense += defense);
  }

  @Override
  public String toString() {
    return String.format("%s %d", super.toString(), defense);
  }
}
