package com.dcg.player;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;

public class AdjustHp extends AbstractCommandBuilder {
  private final int hp;
  protected ComponentMapper<Player> mPlayer;

  public AdjustHp(int hp) {
    this.hp = hp;
  }

  @Override
  protected void run() {
    Player player = mPlayer.get(coreSystem.getOwner(sourceEntity));
    player.hp += hp;
  }
}
