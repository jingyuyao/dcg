package com.dcg.battle;

import com.dcg.command.Target;

public class SetUnblockable extends UnitEffectBuilder {
  private final boolean unblockable;

  public SetUnblockable(boolean unblockable) {
    this.unblockable = unblockable;
  }

  @Override
  protected void run(Target target) {
    getUnits(target).forEach(unit -> unit.unblockable = unblockable);
  }
}
