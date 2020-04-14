package com.dcg.effect;

import com.dcg.command.Command;
import com.dcg.turn.TurnSystem;

public class GeneratePower extends Command {
  public final int power;
  protected TurnSystem turnSystem;

  public GeneratePower(int power) {
    this.power = power;
  }

  @Override
  public void run() {
    turnSystem.getCurrentTurn().powerPool += power;
  }
}
