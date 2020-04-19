package com.dcg.target;

import com.dcg.command.Input;
import java.util.Collections;
import java.util.List;

public class OriginEntity implements TargetFunction {
  @Override
  public Target apply(Integer originEntity, Input input) {
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
