package com.dcg.target;

import com.dcg.command.Input;
import java.util.Collections;
import java.util.List;

public class SourceEntity implements TargetFunction {
  @Override
  public Target apply(Integer sourceEntity, Input input) {
    return new Target() {
      @Override
      public int getOrigin() {
        return sourceEntity;
      }

      @Override
      public List<Integer> getTargets() {
        return Collections.singletonList(sourceEntity);
      }
    };
  }
}
