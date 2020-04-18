package com.dcg.condition;

import com.artemis.ComponentMapper;
import com.dcg.game.CoreSystem;
import com.dcg.player.Turn;
import java.util.function.BooleanSupplier;

public class MinPower implements BooleanSupplier {
  private final int power;
  protected CoreSystem coreSystem;
  protected ComponentMapper<Turn> mTurn;

  public MinPower(int power) {
    this.power = power;
  }

  @Override
  public boolean getAsBoolean() {
    return coreSystem
        .getCurrentPlayerEntity()
        .mapToObj(mTurn::get)
        .anyMatch(turn -> turn.powerPool >= power);
  }
}
