package com.dcg.targetsource;

import com.artemis.Aspect;
import com.dcg.battle.Unit;
import com.dcg.game.CoreSystem;
import java.util.List;
import java.util.stream.Collectors;

public class DefendingUnits implements TargetSource {
  protected CoreSystem coreSystem;

  @Override
  public List<Integer> get(int sourceEntity, List<Integer> input) {
    return coreSystem
        .getCurrentPlayerEntity()
        .flatMap(playerEntity -> coreSystem.getDescendants(playerEntity, Aspect.all(Unit.class)))
        .boxed()
        .collect(Collectors.toList());
  }
}
