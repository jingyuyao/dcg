package com.dcg.battle;

import com.dcg.command.CommandArgs;
import java.util.List;

public class SetLifeSteal extends UnitEffectBuilder {
  private final boolean lifeSteal;

  public SetLifeSteal(boolean lifeSteal) {
    this.lifeSteal = lifeSteal;
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    getUnits(targets).forEach(unit -> unit.lifeSteal = lifeSteal);
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), lifeSteal);
  }
}
