package com.dcg.player;

import com.dcg.command.Command;
import com.dcg.turn.TurnSystem;

public class AdjustPower extends Command {
  private final int power;
  protected TurnSystem turnSystem;

  public AdjustPower(int power) {
    this.power = power;
  }

  @Override
  public void run() {
    turnSystem.getCurrentTurn().powerPool += power;
  }

  @Override
  public String toString() {
    return String.format("%s %d", super.toString(), power);
  }
}
