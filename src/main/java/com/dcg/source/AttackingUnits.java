package com.dcg.source;

import com.dcg.command.Input;
import com.dcg.command.Target;
import com.dcg.game.CoreSystem;
import java.util.stream.Collectors;

public class AttackingUnits implements CommandSource {
  protected CoreSystem coreSystem;

  @Override
  public Target apply(Integer sourceEntity, Input input) {
    return () -> coreSystem.getAttackingEntities().boxed().collect(Collectors.toList());
  }
}
