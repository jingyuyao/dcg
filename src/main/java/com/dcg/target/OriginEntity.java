package com.dcg.target;

import java.util.Collections;
import java.util.List;

public class OriginEntity extends TargetSource {
  @Override
  protected List<Integer> transform(int originEntity, List<Integer> input) {
    return Collections.singletonList(originEntity);
  }
}
