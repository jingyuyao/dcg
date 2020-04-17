package com.dcg.battle;

public class AdjustDefense extends AdjustUnit {
  private final int defense;

  public AdjustDefense(int defense) {
    this.defense = defense;
  }

  @Override
  protected void run() {
    mUnit.get(getTargetEntity()).defense += defense;
  }

  @Override
  public String toString() {
    return String.format("%s %d", super.toString(), defense);
  }
}
