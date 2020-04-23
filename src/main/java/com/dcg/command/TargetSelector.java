package com.dcg.command;

import java.util.List;

public interface TargetSelector {
  List<Integer> select(int originEntity, List<Integer> allowedTargets, List<Integer> input);
}
