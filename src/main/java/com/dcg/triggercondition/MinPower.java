package com.dcg.triggercondition;

import com.dcg.game.CoreSystem;
import java.util.List;

/** Checks whether at least the given amount of Power was generated this turn. */
public class MinPower implements TriggerCondition {
  private final int power;
  protected CoreSystem coreSystem;

  public MinPower(int power) {
    this.power = power;
  }

  @Override
  public boolean test(int originEntity, List<Integer> allowedTargets) {
    return coreSystem.getTurn().powerGenerated >= power;
  }
}
