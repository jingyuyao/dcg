package com.dcg.targetsource;

import java.util.List;

public class Inputs implements TargetSource {
  @Override
  public List<Integer> get(int sourceEntity, List<Integer> input) {
    return input;
  }
}
