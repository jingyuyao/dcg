package com.dcg.battle;

import com.dcg.command.CommandArgs;
import java.util.List;

public class SetBerserk extends UnitEffectBuilder {
  private final boolean berserk;

  public SetBerserk(boolean berserk) {
    this.berserk = berserk;
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    getUnits(targets).forEach(unit -> unit.berserk = berserk);
  }
}
