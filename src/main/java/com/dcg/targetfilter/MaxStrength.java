package com.dcg.targetfilter;

import com.artemis.ComponentMapper;
import com.dcg.battle.Unit;

public class MaxStrength implements TargetFilter {
  private final int strength;
  protected ComponentMapper<Unit> mUnit;

  public MaxStrength(int strength) {
    this.strength = strength;
  }

  @Override
  public boolean test(int originEntity, int target) {
    return mUnit.get(target).strength <= strength;
  }
}
