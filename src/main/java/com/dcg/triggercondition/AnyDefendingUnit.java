package com.dcg.triggercondition;

import com.artemis.ComponentMapper;
import com.dcg.battle.Unit;
import com.dcg.game.CoreSystem;
import java.util.function.Predicate;

public class AnyDefendingUnit implements TriggerCondition {
  private final Predicate<Unit> predicate;
  protected CoreSystem coreSystem;
  protected ComponentMapper<Unit> mUnit;

  public AnyDefendingUnit() {
    predicate = unit -> true;
  }

  public AnyDefendingUnit(Predicate<Unit> predicate) {
    this.predicate = predicate;
  }

  @Override
  public boolean test(Integer originEntity) {
    return coreSystem.getDefendingEntities().map(mUnit::get).anyMatch(predicate);
  }
}
