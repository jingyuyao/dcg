package com.dcg.targetsource;

import com.dcg.game.CoreSystem;
import java.util.List;
import java.util.stream.Collectors;

public class AttackingUnits implements TargetSource {
  protected CoreSystem coreSystem;

  @Override
  public List<Integer> get(int sourceEntity, List<Integer> input) {
    return coreSystem.getAttackingEntities().boxed().collect(Collectors.toList());
  }
}
