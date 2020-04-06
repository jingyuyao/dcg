package com.dcg.player;

import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.dcg.command.CommandDeque;
import com.dcg.turn.Turn;

@All({Player.class, Turn.class})
public class PlayerTurnSystem extends BaseEntitySystem {

  @Wire
  CommandDeque commandDeque;
  ComponentMapper<Player> mPlayer;

  @Override
  protected void inserted(int entityId) {
    super.inserted(entityId);
    Player player = mPlayer.get(entityId);
    commandDeque.addLast(new DrawCards(player, 5));
  }

  @Override
  protected void removed(int entityId) {
    super.removed(entityId);
    commandDeque.addLast(new DiscardHand(mPlayer.get(entityId)));
  }

  @Override
  protected void processSystem() {
    // No-op.
  }
}
