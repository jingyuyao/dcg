package com.dcg.target;

import java.util.List;

public class Inputs extends TargetSource {
  // TODO: bug, need to restrict the number of inputs
  @Override
  protected List<Integer> transform(int originEntity, List<Integer> input) {
    return input;
  }
}
