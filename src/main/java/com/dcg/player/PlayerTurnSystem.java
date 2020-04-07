package com.dcg.player;

import com.artemis.BaseEntitySystem;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.dcg.command.CommandDeque;
import com.dcg.turn.Turn;

@All({Player.class, Turn.class})
public class PlayerTurnSystem extends BaseEntitySystem {

  @Wire
  CommandDeque commandDeque;

  @Override
  protected void inserted(int entityId) {
    super.inserted(entityId);
    commandDeque.addLast(new TryDrawCard(entityId));
    commandDeque.addLast(new TryDrawCard(entityId));
    commandDeque.addLast(new TryDrawCard(entityId));
    commandDeque.addLast(new TryDrawCard(entityId));
    commandDeque.addLast(new TryDrawCard(entityId));
  }

  @Override
  protected void removed(int entityId) {
    super.removed(entityId);
    commandDeque.addLast(new DiscardHand(entityId));
  }

  @Override
  protected void processSystem() {
    // No-op.
  }
}
