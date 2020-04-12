package com.dcg.player;

import com.artemis.ComponentMapper;
import com.dcg.command.Command;
import com.dcg.turn.TurnSystem;

public class AdjustPower extends Command {
  private final int power;
  TurnSystem turnSystem;
  protected ComponentMapper<Player> mPlayer;

  public AdjustPower(int power) {
    this.power = power;
  }

  @Override
  public void run() {
    mPlayer.get(turnSystem.getCurrentPlayerEntity()).power += power;
  }

  @Override
  public String toString() {
    return String.format("%s %d", super.toString(), power);
  }
}
