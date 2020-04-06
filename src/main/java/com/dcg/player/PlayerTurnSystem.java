package com.dcg.player;

import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.dcg.command.CommandSystem;
import com.dcg.turn.Turn;

@All({Player.class, Turn.class})
public class PlayerTurnSystem extends BaseEntitySystem {

  CommandSystem commandSystem;
  ComponentMapper<Player> mPlayer;

  @Override
  protected void inserted(int entityId) {
    super.inserted(entityId);
    Player player = mPlayer.get(entityId);
    commandSystem.run(new DrawCards(player, 5));
  }

  @Override
  protected void removed(int entityId) {
    super.removed(entityId);
    commandSystem.run(new DiscardHand(mPlayer.get(entityId)));
  }

  @Override
  protected void processSystem() {
    // No-op.
  }
}
