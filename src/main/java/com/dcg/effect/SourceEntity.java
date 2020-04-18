package com.dcg.effect;

import java.util.Collections;
import java.util.List;

public class SourceEntity implements TargetSource {
  @Override
  public List<Integer> get(int sourceEntity, List<Integer> input) {
    return Collections.singletonList(sourceEntity);
  }
}
