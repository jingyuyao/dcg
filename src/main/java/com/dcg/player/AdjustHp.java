package com.dcg.player;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;

// TODO: make a base class for adjusting the player as well
public class AdjustHp extends AbstractCommandBuilder {
  private final int hp;
  protected ComponentMapper<Player> mPlayer;

  public AdjustHp(int hp) {
    this.hp = hp;
  }

  @Override
  protected void run() {
    Player player = mPlayer.get(coreSystem.getRoot(sourceEntity));
    player.hp += hp;
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), hp);
  }
}
