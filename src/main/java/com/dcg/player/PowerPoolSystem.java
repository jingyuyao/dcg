package com.dcg.player;

import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.dcg.turn.Turn;

@All({Player.class, Turn.class})
public class PowerPoolSystem extends BaseEntitySystem {
  protected ComponentMapper<Player> mPlayer;

  @Override
  protected void removed(int entityId) {
    super.removed(entityId);
    mPlayer.get(entityId).powerPool = 0;
  }

  @Override
  protected void processSystem() {}
}
