package com.dcg.ownership;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.utils.IntBag;
import java.util.ArrayList;
import java.util.List;

public class OwnershipSystem extends BaseSystem {
  AspectSubscriptionManager manager;
  ComponentMapper<Owned> mOwned;

  /** Filters the aspect for the owner. Automatically adds one(Owned) to the builder. */
  public List<Integer> getOwnedBy(Aspect.Builder aspectBuilder, int ownerEntity) {
    List<Integer> owned = new ArrayList<>();
    IntBag entities = manager.get(aspectBuilder.one(Owned.class)).getEntities();
    for (int i = 0; i < entities.size(); i++) {
      int entity = entities.get(i);
      if (ownerEntity == mOwned.get(entity).owner) {
        owned.add(entity);
      }
    }
    return owned;
  }

  @Override
  protected void processSystem() {
    // No-op
  }
}
