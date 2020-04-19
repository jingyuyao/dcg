package com.dcg.target;

import com.dcg.game.CoreSystem;
import java.util.Collections;
import java.util.List;

public class OriginEntityRoot implements TargetFunction {
  protected CoreSystem coreSystem;

  @Override
  public Target apply(Integer originEntity, List<Integer> inputs) {
    return new Target() {
      @Override
      public int getOrigin() {
        return originEntity;
      }

      @Override
      public List<Integer> getTargets() {
        return Collections.singletonList(coreSystem.getRoot(originEntity));
      }
    };
  }
}
