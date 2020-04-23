package com.dcg.command;

import com.dcg.game.CoreSystem;
import java.util.Collections;
import java.util.List;

public class OriginRootSelector implements TargetSelector {
  protected CoreSystem coreSystem;

  @Override
  public List<Integer> select(int originEntity, List<Integer> allowedTargets, List<Integer> input) {
    return Collections.singletonList(coreSystem.getRoot(originEntity));
  }
}
