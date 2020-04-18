package com.dcg.battle;

import java.util.List;

public class SetUnblockable extends UnitEffectBuilder {
  private final boolean unblockable;

  public SetUnblockable(boolean unblockable) {
    this.unblockable = unblockable;
  }

  @Override
  protected void run(List<Integer> input) {
    getUnits(input).forEach(unit -> unit.unblockable = unblockable);
  }
}
