package com.dcg.player;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import java.util.List;

public class AdjustPower extends AbstractCommandBuilder {
  protected ComponentMapper<Turn> mTurn;

  public AdjustPower(int power) {
    setCommandValue(() -> power);
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, int value) {
    coreSystem.getCurrentPlayerEntity().map(mTurn::get).forEach(turn -> turn.powerPool += value);
  }
}
