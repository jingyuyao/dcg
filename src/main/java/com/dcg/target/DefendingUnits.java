package com.dcg.target;

import com.dcg.command.Input;
import com.dcg.game.CoreSystem;
import java.util.List;
import java.util.stream.Collectors;

public class DefendingUnits implements TargetFunction {
  protected CoreSystem coreSystem;

  @Override
  public Target apply(Integer originEntity, Input input) {
    return new Target() {
      @Override
      public int getOrigin() {
        return originEntity;
      }

      @Override
      public List<Integer> getTargets() {
        return coreSystem.getDefendingEntities().boxed().collect(Collectors.toList());
      }
    };
  }
}
