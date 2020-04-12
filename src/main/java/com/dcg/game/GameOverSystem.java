package com.dcg.game;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.dcg.player.Player;

@All(Player.class)
public class GameOverSystem extends IteratingSystem {
  private boolean isOver = false;
  protected ComponentMapper<Player> mPlayer;

  @Override
  protected void process(int entityId) {
    Player player = mPlayer.get(entityId);
    if (player.hp <= 0) {
      isOver = true;
    }
  }

  public boolean isOver() {
    return isOver;
  }
}
