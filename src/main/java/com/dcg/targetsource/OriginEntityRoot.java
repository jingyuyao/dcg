package com.dcg.targetsource;

import com.dcg.game.CoreSystem;
import java.util.stream.Stream;

public class OriginEntityRoot extends TargetSource {
  protected CoreSystem coreSystem;

  @Override
  protected Stream<Integer> getSource(int originEntity) {
    return Stream.of(coreSystem.getRoot(originEntity));
  }
}
