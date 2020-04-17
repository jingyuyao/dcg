package com.dcg.turn;

import com.dcg.command.CommandBase;

public class AdjustPower extends CommandBase {
  private final int power;
  protected TurnSystem turnSystem;

  public AdjustPower(int power) {
    this.power = power;
  }

  @Override
  protected void run() {
    turnSystem.getTurn().powerPool += power;
  }

  @Override
  public String toString() {
    return String.format("%s %d", super.toString(), power);
  }
}
