package com.dcg.player;

import com.artemis.BaseEntitySystem;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.utils.IntBag;
import com.dcg.command.CommandChain;
import com.dcg.turn.Turn;

@All({Player.class, Turn.class})
public class PlayerTurnSystem extends BaseEntitySystem {

  @Wire CommandChain commandChain;

  public int getCurrentPlayerEntity() {
    IntBag entities = getEntityIds();
    assert entities.size() < 2;
    return entities.size() == 1 ? getEntityIds().get(0) : -1;
  }

  @Override
  protected void inserted(int entityId) {
    super.inserted(entityId);
    commandChain.addEnd(new DrawCard(entityId));
    commandChain.addEnd(new DrawCard(entityId));
    commandChain.addEnd(new DrawCard(entityId));
    commandChain.addEnd(new DrawCard(entityId));
    commandChain.addEnd(new DrawCard(entityId));
  }

  @Override
  protected void removed(int entityId) {
    super.removed(entityId);
    commandChain.addEnd(new DiscardPlayArea(entityId));
  }

  @Override
  protected void processSystem() {
    // No-op.
  }
}
