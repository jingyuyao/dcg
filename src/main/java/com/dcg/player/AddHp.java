package com.dcg.player;

import com.artemis.ComponentMapper;
import com.dcg.command.CommandBase;
import com.dcg.game.OwnershipSystem;

public class AddHp extends CommandBase {
  private final int hp;
  protected OwnershipSystem ownershipSystem;
  protected ComponentMapper<Player> mPlayer;

  public AddHp(int hp) {
    this.hp = hp;
  }

  @Override
  protected void run() {
    Player player = mPlayer.get(ownershipSystem.getOwner(sourceEntity));
    player.hp += hp;
  }
}
