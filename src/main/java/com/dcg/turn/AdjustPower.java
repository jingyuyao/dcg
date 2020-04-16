package com.dcg.turn;

import com.dcg.command.Command;

public class AdjustPower extends Command {
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
