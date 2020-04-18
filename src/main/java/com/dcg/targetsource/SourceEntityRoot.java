package com.dcg.targetsource;

import com.dcg.game.CoreSystem;
import java.util.Collections;
import java.util.List;

public class SourceEntityRoot implements TargetSource {
  protected CoreSystem coreSystem;

  @Override
  public List<Integer> get(int sourceEntity, List<Integer> input) {
    return Collections.singletonList(coreSystem.getRoot(sourceEntity));
  }
}
