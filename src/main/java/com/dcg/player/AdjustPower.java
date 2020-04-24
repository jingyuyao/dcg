package com.dcg.player;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandArgs;
import com.dcg.turn.Turn;
import java.util.List;

public class AdjustPower extends AbstractCommandBuilder {
  protected ComponentMapper<Turn> mTurn;

  public AdjustPower(int power) {
    setIntArgSupplier(() -> power);
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    coreSystem
        .getCurrentPlayerEntity()
        .map(mTurn::get)
        .forEach(turn -> turn.powerPool += args.getInt());
  }
}
