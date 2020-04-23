package com.dcg.command;

import java.util.Collections;
import java.util.List;

public class OriginSelector implements TargetSelector {
  @Override
  public List<Integer> select(int originEntity, List<Integer> allowedTargets, List<Integer> input) {
    return Collections.singletonList(originEntity);
  }
}
