package com.dcg.battle;

public class SetBerserk extends UnitEffectBuilder {
  private final boolean berserk;

  public SetBerserk(boolean berserk) {
    this.berserk = berserk;
  }

  @Override
  protected void run() {
    getTargetComponents().forEach(unit -> unit.berserk = berserk);
  }
}
