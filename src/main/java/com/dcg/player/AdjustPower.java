package com.dcg.player;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.targetsource.SourceEntityRoot;
import java.util.List;

public class AdjustPower extends AbstractCommandBuilder {
  private final int power;
  protected ComponentMapper<Turn> mTurn;

  public AdjustPower(int power) {
    this.power = power;
    setTargetSource(new SourceEntityRoot());
    addTargetConditions(input -> input.stream().allMatch(mTurn::has));
  }

  @Override
  protected void run(List<Integer> input) {
    getTargetEntities(input).stream().map(mTurn::get).forEach(turn -> turn.powerPool += power);
  }

  @Override
  public String toString() {
    return String.format("%s %d", super.toString(), power);
  }
}
