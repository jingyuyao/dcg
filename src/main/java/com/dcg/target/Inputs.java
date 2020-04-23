package com.dcg.target;

import com.dcg.game.CoreSystem;
import java.util.List;

abstract class Inputs extends TargetSource {
  private final int minInputs;
  private final int maxInputs;
  protected CoreSystem coreSystem;

  public Inputs() {
    this(1, 1);
  }

  public Inputs(int maxInputs) {
    this(1, maxInputs);
  }

  public Inputs(int minInputs, int maxInputs) {
    this.minInputs = minInputs;
    this.maxInputs = maxInputs;
  }

  @Override
  public int getMinTargetCount() {
    return minInputs;
  }

  @Override
  public int getMaxTargetCount() {
    return maxInputs;
  }

  @Override
  public abstract List<Integer> getAllowedTargets();

  @Override
  protected List<Integer> transform(int originEntity, List<Integer> input) {
    return input;
  }
}
