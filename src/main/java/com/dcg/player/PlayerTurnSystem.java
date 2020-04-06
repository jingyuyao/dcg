package com.dcg.player;

import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.dcg.command.CommandQueue;
import com.dcg.turn.Turn;

@All({Player.class, Turn.class})
public class PlayerTurnSystem extends BaseEntitySystem {

  @Wire
  CommandQueue commandQueue;
  ComponentMapper<Player> mPlayer;

  @Override
  protected void inserted(int entityId) {
    super.inserted(entityId);
    Player player = mPlayer.get(entityId);
    commandQueue.add(new DrawCards(player, 5));
  }

  @Override
  protected void removed(int entityId) {
    super.removed(entityId);
    commandQueue.add(new DiscardHand(mPlayer.get(entityId)));
  }

  @Override
  protected void processSystem() {
    // No-op.
  }
}
