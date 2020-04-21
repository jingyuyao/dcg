package com.dcg.target;

import com.dcg.game.CoreSystem;
import java.util.Collections;
import java.util.List;

public class OriginEntityRoot extends TargetSource {
  protected CoreSystem coreSystem;

  @Override
  protected List<Integer> transform(int originEntity, List<Integer> input) {
    return Collections.singletonList(coreSystem.getRoot(originEntity));
  }
}
