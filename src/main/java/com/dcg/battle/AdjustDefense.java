package com.dcg.battle;

import java.util.List;

public class AdjustDefense extends UnitEffectBuilder {
  private final int defense;

  public AdjustDefense(int defense) {
    this.defense = defense;
  }

  @Override
  protected void run(List<Integer> input) {
    getTargetComponents(input).forEach(unit -> unit.defense += defense);
  }

  @Override
  public String toString() {
    return String.format("%s %d", super.toString(), defense);
  }
}
