package com.dcg.game;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.dcg.player.Player;

@All(Player.class)
public class GameOverSystem extends IteratingSystem {
  private boolean isOver = false;
  ComponentMapper<Player> mPlayer;

  @Override
  protected void process(int entityId) {
    if (mPlayer.get(entityId).hp <= 0) {
      isOver = true;
    }
  }

  public boolean isOver() {
    return isOver;
  }
}
