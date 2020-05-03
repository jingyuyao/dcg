package com.dcg.targetfilter;

import com.artemis.ComponentMapper;
import com.dcg.battle.Unit;
import java.util.function.Predicate;

public class UnitFilter implements TargetFilter {
  private final Predicate<Unit> predicate;
  protected ComponentMapper<Unit> mUnit;

  public UnitFilter(Predicate<Unit> predicate) {
    this.predicate = predicate;
  }

  @Override
  public boolean test(int originEntity, int target) {
    return predicate.test(mUnit.get(originEntity));
  }
}
