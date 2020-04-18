package com.dcg.targetsource;

import com.dcg.command.Input;
import com.dcg.command.Target;
import com.dcg.game.CoreSystem;
import java.util.Collections;

public class SourceEntityRoot implements TargetSource {
  protected CoreSystem coreSystem;

  @Override
  public Target apply(Integer sourceEntity, Input input) {
    return () -> Collections.singletonList(coreSystem.getRoot(sourceEntity));
  }
}
