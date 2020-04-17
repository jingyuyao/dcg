package com.dcg.player;

import com.artemis.ComponentMapper;
import com.dcg.effect.AbstractEffectBuilder;

public class AdjustPower extends AbstractEffectBuilder<Turn> {
  private final int power;
  protected ComponentMapper<Turn> mTurn;

  public AdjustPower(int power) {
    this.power = power;
  }

  @Override
  protected void run() {
    getTargetComponents().forEach(turn -> turn.powerPool += power);
  }

  @Override
  protected ComponentMapper<Turn> getComponentMapper() {
    return mTurn;
  }

  @Override
  protected int transformTargetEntity(int targetEntity) {
    return coreSystem.getRoot(targetEntity);
  }

  @Override
  public String toString() {
    return String.format("%s %d", super.toString(), power);
  }
}
