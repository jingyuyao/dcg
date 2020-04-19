package com.dcg.source;

import com.dcg.command.Input;
import com.dcg.command.Target;
import com.dcg.game.CoreSystem;
import java.util.Collections;
import java.util.List;

public class SourceEntityRoot implements TargetFunction {
  protected CoreSystem coreSystem;

  @Override
  public Target apply(Integer sourceEntity, Input input) {
    return new Target() {
      @Override
      public int getFrom() {
        return sourceEntity;
      }

      @Override
      public List<Integer> getTo() {
        return Collections.singletonList(coreSystem.getRoot(sourceEntity));
      }
    };
  }
}
