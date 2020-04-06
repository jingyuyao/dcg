package com.dcg.debug;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.dcg.player.Player;
import com.dcg.turn.Turn;

@All({Player.class, Turn.class})
public class DebugSystem extends IteratingSystem {
  ComponentMapper<Player> mPlayer;

  @Override
  protected void process(int entityId) {
    System.out.println("current: " + mPlayer.get(entityId));
  }
}
