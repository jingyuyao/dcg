package com.dcg.player;

import com.artemis.ComponentMapper;
import com.dcg.command.Target;

public class AdjustPower extends PlayerEffect {
  private final int power;
  protected ComponentMapper<Turn> mTurn;

  public AdjustPower(int power) {
    this.power = power;
    addTargetConditions(target -> mTurn.has(target.getFrom()));
  }

  @Override
  protected void run(Target target) {
    mTurn.get(target.getFrom()).powerPool += power;
  }

  @Override
  public String toString() {
    return String.format("%s %d", super.toString(), power);
  }
}
