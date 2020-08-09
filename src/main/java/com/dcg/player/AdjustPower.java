package com.dcg.player;

import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandData;
import com.dcg.turn.Turn;

public class AdjustPower extends AbstractCommandBuilder {
  private AdjustPower(int power) {
    setIntArgSupplier(() -> power);
  }

  public static AdjustPower power(int power) {
    return new AdjustPower(power);
  }

  @Override
  protected void run(CommandData data) {
    Turn turn = coreSystem.getTurn();
    turn.powerPool += data.getInt();
  }

  @Override
  protected String getDescription(CommandData data) {
    return String.format("adds %d Power", data.getInt());
  }

  @Override
  protected boolean isClientVisible(CommandData data) {
    // Only show adding power since buy/warp already shows subtracting power
    return data.getInt() > 0;
  }
}
