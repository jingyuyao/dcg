package com.dcg.targetsource;

import com.artemis.Aspect;
import com.dcg.battle.Unit;
import com.dcg.command.Input;
import com.dcg.command.Target;
import com.dcg.game.CoreSystem;
import java.util.stream.Collectors;

public class DefendingUnits implements TargetSource {
  protected CoreSystem coreSystem;

  @Override
  public Target apply(Integer sourceEntity, Input input) {
    return () ->
        coreSystem
            .getCurrentPlayerEntity()
            .flatMap(
                playerEntity -> coreSystem.getDescendants(playerEntity, Aspect.all(Unit.class)))
            .boxed()
            .collect(Collectors.toList());
  }
}
