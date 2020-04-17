package com.dcg.battle;

public class SetBerserk extends AdjustUnit {
  private final boolean berserk;

  public SetBerserk(boolean berserk) {
    this.berserk = berserk;
  }

  @Override
  protected void run() {
    mUnit.get(getTargetEntity()).berserk = berserk;
  }
}
