package com.dcg.battle;

import com.dcg.command.CommandArgs;
import java.util.List;

public class SetUnblockable extends UnitEffectBuilder {
  private final boolean unblockable;

  public SetUnblockable(boolean unblockable) {
    this.unblockable = unblockable;
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    getUnits(targets).forEach(unit -> unit.unblockable = unblockable);
  }
}
