package com.dcg.source;

import com.dcg.command.Target;
import com.dcg.game.CoreSystem;
import java.util.stream.Collectors;

public class TotalAttackingUnits implements EffectSource {
  protected CoreSystem coreSystem;

  @Override
  public Target get() {
    return () -> coreSystem.getAttackingEntities().boxed().collect(Collectors.toList());
  }
}
