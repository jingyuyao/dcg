package com.dcg.source;

import com.dcg.command.Input;
import com.dcg.command.Target;
import com.dcg.game.CoreSystem;
import java.util.stream.Collectors;

public class DefendingUnits implements CommandSource {
  protected CoreSystem coreSystem;

  @Override
  public Target apply(Integer sourceEntity, Input input) {
    return () -> coreSystem.getDefendingEntities().boxed().collect(Collectors.toList());
  }
}
