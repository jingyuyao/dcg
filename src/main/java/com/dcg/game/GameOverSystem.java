package com.dcg.game;

import com.artemis.BaseEntitySystem;
import com.artemis.annotations.All;
import com.dcg.player.Player;

@All(Player.class)
public class GameOverSystem extends BaseEntitySystem {
  private boolean isOver = false;

  public boolean isOver() {
    return isOver;
  }

  @Override
  protected void processSystem() {
    isOver = getEntityIds().size() <= 1;
  }
}
