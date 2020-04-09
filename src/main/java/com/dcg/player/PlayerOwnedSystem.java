package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.utils.IntBag;
import java.util.ArrayList;
import java.util.List;

public class PlayerOwnedSystem extends BaseSystem {
  AspectSubscriptionManager manager;
  ComponentMapper<PlayerOwned> mPlayerOwned;

  public List<Integer> filter(Aspect.Builder aspectBuilder, int playerEntity) {
    List<Integer> playerOwned = new ArrayList<>();
    IntBag entities = manager.get(aspectBuilder).getEntities();
    for (int i = 0; i < entities.size(); i++) {
      int entity = entities.get(i);
      if (playerEntity == mPlayerOwned.get(entity).playerEntity) {
        playerOwned.add(entity);
      }
    }
    return playerOwned;
  }

  @Override
  protected void processSystem() {
    // No-op
  }
}
