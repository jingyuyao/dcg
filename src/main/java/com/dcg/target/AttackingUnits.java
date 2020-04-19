package com.dcg.target;

import com.dcg.command.Input;
import com.dcg.game.CoreSystem;
import java.util.List;
import java.util.stream.Collectors;

public class AttackingUnits implements TargetFunction {
  protected CoreSystem coreSystem;

  @Override
  public Target apply(Integer sourceEntity, Input input) {
    return new Target() {
      @Override
      public int getOrigin() {
        return sourceEntity;
      }

      @Override
      public List<Integer> getTargets() {
        return coreSystem.getAttackingEntities().boxed().collect(Collectors.toList());
      }
    };
  }
}
