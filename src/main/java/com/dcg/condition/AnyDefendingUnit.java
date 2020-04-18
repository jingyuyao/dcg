package com.dcg.condition;

import com.artemis.ComponentMapper;
import com.dcg.battle.Unit;
import com.dcg.game.CoreSystem;
import java.util.function.Predicate;

public class AnyDefendingUnit implements WorldCondition {
  private final Predicate<Unit> predicate;
  protected ComponentMapper<Unit> mUnit;

  public AnyDefendingUnit() {
    predicate = unit -> true;
  }

  public AnyDefendingUnit(Predicate<Unit> predicate) {
    this.predicate = predicate;
  }

  @Override
  public boolean test(CoreSystem coreSystem) {
    return coreSystem.getDefendingEntities().mapToObj(mUnit::get).anyMatch(predicate);
  }
}
