package com.dcg.effect;

import com.dcg.command.CommandValue;
import com.dcg.game.CoreSystem;

public class TotalAttackingUnits implements CommandValue {
  protected CoreSystem coreSystem;

  @Override
  public Integer get() {
    return (int) coreSystem.getAttackingEntities().count();
  }
}
