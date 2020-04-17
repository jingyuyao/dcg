package com.dcg.battle;

public class AdjustDefense extends UnitEffectBuilder {
  private final int defense;

  public AdjustDefense(int defense) {
    this.defense = defense;
  }

  @Override
  protected void run() {
    getTargetComponents().forEach(unit -> unit.defense += defense);
  }

  @Override
  public String toString() {
    return String.format("%s %d", super.toString(), defense);
  }
}
