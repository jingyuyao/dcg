package com.dcg.turn;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.dcg.player.Player;

@All({Player.class, Turn.class})
public class TurnSystem extends IteratingSystem {
  ComponentMapper<Player> mPlayer;

  @Override
  protected void process(int entityId) {
    System.out.println("current player: " + mPlayer.get(entityId).name);
  }
}
