package com.dcg.target;

import java.util.Collections;
import java.util.List;

public class OriginEntity implements TargetFunction {
  @Override
  public Target apply(Integer originEntity, List<Integer> inputs) {
    return new Target() {
      @Override
      public int getOrigin() {
        return originEntity;
      }

      @Override
      public List<Integer> getTargets() {
        return Collections.singletonList(originEntity);
      }
    };
  }
}
