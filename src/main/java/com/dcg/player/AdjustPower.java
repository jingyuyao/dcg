package com.dcg.player;

import com.artemis.ComponentMapper;
import com.dcg.effect.AbstractEffectBuilder;
import com.dcg.targetsource.SourceEntityRoot;
import java.util.List;

public class AdjustPower extends AbstractEffectBuilder<Turn> {
  private final int power;
  protected ComponentMapper<Turn> mTurn;

  public AdjustPower(int power) {
    this.power = power;
    setTargetSource(new SourceEntityRoot());
  }

  @Override
  protected void run(List<Integer> input) {
    getTargetComponents(input).forEach(turn -> turn.powerPool += power);
  }

  @Override
  protected ComponentMapper<Turn> getComponentMapper() {
    return mTurn;
  }

  @Override
  public String toString() {
    return String.format("%s %d", super.toString(), power);
  }
}
