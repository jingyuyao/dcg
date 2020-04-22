package com.dcg.target;

import com.dcg.game.CoreSystem;
import java.util.List;
import java.util.stream.Collectors;

public class AllAttackingUnits extends TargetSource {
  protected CoreSystem coreSystem;

  @Override
  protected List<Integer> transform(int originEntity, List<Integer> input) {
    return coreSystem.getAttackingEntities().boxed().collect(Collectors.toList());
  }
}
