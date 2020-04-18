package com.dcg.player;

import com.artemis.ComponentMapper;
import com.dcg.command.Target;

public class AdjustPower extends PlayerEffect {
  private final int power;
  protected ComponentMapper<Turn> mTurn;

  public AdjustPower(int power) {
    this.power = power;
    addTargetConditions(target -> target.get().stream().allMatch(mTurn::has));
  }

  @Override
  protected void run(Target target) {
    target.get().stream().map(mTurn::get).forEach(turn -> turn.powerPool += power);
  }

  @Override
  public String toString() {
    return String.format("%s %d", super.toString(), power);
  }
}
