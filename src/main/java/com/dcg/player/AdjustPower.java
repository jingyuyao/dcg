package com.dcg.player;

import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandData;
import com.dcg.turn.Turn;
import java.util.List;

public class AdjustPower extends AbstractCommandBuilder {
  private AdjustPower(int power) {
    setIntArgSupplier(() -> power);
  }

  public static AdjustPower power(int power) {
    return new AdjustPower(power);
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandData args) {
    Turn turn = coreSystem.getTurn();
    turn.powerPool += args.getInt();
  }
}
