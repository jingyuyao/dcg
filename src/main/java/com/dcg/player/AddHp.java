package com.dcg.player;

import com.artemis.ComponentMapper;
import com.dcg.command.CommandBase;
import com.dcg.game.CoreSystem;

public class AddHp extends CommandBase {
  private final int hp;
  protected CoreSystem coreSystem;
  protected ComponentMapper<Player> mPlayer;

  public AddHp(int hp) {
    this.hp = hp;
  }

  @Override
  protected void run() {
    Player player = mPlayer.get(coreSystem.getOwner(sourceEntity));
    player.hp += hp;
  }
}
