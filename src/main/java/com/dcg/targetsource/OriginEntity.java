package com.dcg.targetsource;

import java.util.stream.Stream;

public class OriginEntity extends TargetSource {
  @Override
  protected Stream<Integer> getSource(int originEntity) {
    return Stream.of(originEntity);
  }
}
