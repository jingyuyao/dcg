package com.dcg.player;

import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandArgs;
import com.dcg.turn.Turn;
import java.util.List;

public class AdjustPower extends AbstractCommandBuilder {
  public AdjustPower(int power) {
    setIntArgSupplier(() -> power);
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    Turn turn = coreSystem.getTurn();
    turn.powerPool += args.getInt();
  }
}
