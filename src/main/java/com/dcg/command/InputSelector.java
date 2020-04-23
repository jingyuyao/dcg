package com.dcg.command;

import java.util.List;

public class InputSelector implements TargetSelector {
  @Override
  public List<Integer> select(int originEntity, List<Integer> allowedTargets, List<Integer> input) {
    return input;
  }
}
