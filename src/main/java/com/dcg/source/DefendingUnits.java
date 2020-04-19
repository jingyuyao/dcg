package com.dcg.source;

import com.dcg.command.Input;
import com.dcg.command.Target;
import com.dcg.game.CoreSystem;
import java.util.List;
import java.util.stream.Collectors;

public class DefendingUnits implements TargetFunction {
  protected CoreSystem coreSystem;

  @Override
  public Target apply(Integer sourceEntity, Input input) {
    return new Target() {
      @Override
      public int getFrom() {
        return sourceEntity;
      }

      @Override
      public List<Integer> getTo() {
        return coreSystem.getDefendingEntities().boxed().collect(Collectors.toList());
      }
    };
  }
}
