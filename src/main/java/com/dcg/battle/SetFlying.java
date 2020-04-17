package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.dcg.command.CommandBase;

public class SetFlying extends CommandBase {
  private final boolean flying;
  protected ComponentMapper<Unit> mUnit;

  public SetFlying(boolean flying) {
    this.flying = flying;
  }

  @Override
  protected void run() {
    mUnit.get(sourceEntity).flying = flying;
  }
}
