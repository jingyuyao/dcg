package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.command.CommandBase;
import com.dcg.game.AspectSystem;

public class AdjustPower extends CommandBase {
  private final int power;
  protected AspectSystem aspectSystem;
  protected ComponentMapper<Turn> mTurn;

  public AdjustPower(int power) {
    this.power = power;
  }

  @Override
  protected void run() {
    aspectSystem
        .getStream(Aspect.all(Turn.class))
        .mapToObj(mTurn::get)
        .forEach(turn -> turn.powerPool += power);
  }

  @Override
  public String toString() {
    return String.format("%s %d", super.toString(), power);
  }
}
