package com.dcg.target;

import com.dcg.game.CoreSystem;
import java.util.List;
import java.util.stream.Collectors;

public class DefendingUnits extends TargetSource {
  protected CoreSystem coreSystem;

  @Override
  protected List<Integer> transform(int originEntity, List<Integer> input) {
    return coreSystem.getDefendingEntities().boxed().collect(Collectors.toList());
  }
}
