package com.dcg.triggercondition;

import com.artemis.ComponentMapper;
import com.dcg.game.CoreSystem;
import com.dcg.turn.Turn;
import java.util.List;

public class MinPower implements TriggerCondition {
  private final int power;
  protected CoreSystem coreSystem;
  protected ComponentMapper<Turn> mTurn;

  public MinPower(int power) {
    this.power = power;
  }

  @Override
  public boolean test(int originEntity, List<Integer> allowedTargets) {
    return coreSystem
        .getCurrentPlayerEntity()
        .map(mTurn::get)
        .anyMatch(turn -> turn.powerPool >= power);
  }
}
