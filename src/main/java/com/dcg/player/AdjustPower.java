package com.dcg.player;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import java.util.List;

public class AdjustPower extends AbstractCommandBuilder {
  private final int power;
  protected ComponentMapper<Turn> mTurn;

  public AdjustPower(int power) {
    this.power = power;
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, int value) {
    coreSystem.getCurrentPlayerEntity().map(mTurn::get).forEach(turn -> turn.powerPool += power);
  }

  @Override
  public String toString() {
    return String.format("%s %d", super.toString(), power);
  }
}
