package com.dcg.condition;

import com.artemis.ComponentMapper;
import com.dcg.game.CoreSystem;
import com.dcg.player.Turn;

public class MinPower implements TriggerCondition {
  private final int power;
  protected ComponentMapper<Turn> mTurn;

  public MinPower(int power) {
    this.power = power;
  }

  @Override
  public boolean test(CoreSystem coreSystem) {
    return coreSystem
        .getCurrentPlayerEntity()
        .mapToObj(mTurn::get)
        .anyMatch(turn -> turn.powerPool >= power);
  }
}
