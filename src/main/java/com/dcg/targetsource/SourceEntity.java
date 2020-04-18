package com.dcg.targetsource;

import com.dcg.command.Input;
import com.dcg.command.Target;
import java.util.Collections;

public class SourceEntity implements TargetSource {
  @Override
  public Target apply(Integer sourceEntity, Input input) {
    return () -> Collections.singletonList(sourceEntity);
  }
}
