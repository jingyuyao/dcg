package com.dcg.player;

import com.artemis.ComponentMapper;
import com.dcg.command.Command;

public class Damage extends Command {
  private final int playerEntity;
  private final int amount;
  ComponentMapper<Player> mPlayer;

  public Damage(int playerEntity, int amount) {
    this.playerEntity = playerEntity;
    this.amount = amount;
  }

  @Override
  public void run() {
    mPlayer.get(playerEntity).hp -= amount;
  }

  @Override
  public String toString() {
    return super.toString() + mPlayer.get(playerEntity).name + " by " + amount;
  }
}
