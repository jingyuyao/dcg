package com.dcg.player;

import com.artemis.BaseEntitySystem;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.dcg.command.CommandChain;
import com.dcg.turn.Turn;

@All({Player.class, Turn.class})
public class PlayerTurnSystem extends BaseEntitySystem {

  @Wire CommandChain commandChain;

  @Override
  protected void inserted(int entityId) {
    super.inserted(entityId);
    commandChain.addEnd(new TryDrawCard(entityId));
    commandChain.addEnd(new TryDrawCard(entityId));
    commandChain.addEnd(new TryDrawCard(entityId));
    commandChain.addEnd(new TryDrawCard(entityId));
    commandChain.addEnd(new TryDrawCard(entityId));
  }

  @Override
  protected void removed(int entityId) {
    super.removed(entityId);
    commandChain.addEnd(new DiscardHand(entityId));
  }

  @Override
  protected void processSystem() {
    // No-op.
  }
}
