package com.dcg.ownership;

import com.artemis.Aspect.Builder;
import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.dcg.util.AspectSystem;
import java.util.List;
import java.util.stream.Collectors;

public class OwnershipSystem extends BaseSystem {
  AspectSystem aspectSystem;
  ComponentMapper<Owned> mOwned;

  /** Filters the aspect for the owner. Automatically adds one(Owned) to the builder. */
  public List<Integer> getOwnedBy(int ownerEntity, Builder aspectBuilder) {
    return aspectSystem.get(aspectBuilder.one(Owned.class)).stream()
        .filter(e -> ownerEntity == mOwned.get(e).owner)
        .collect(Collectors.toList());
  }

  public boolean isOwnedBy(int ownerEntity, int entity) {
    return mOwned.has(entity) && mOwned.get(entity).owner == ownerEntity;
  }

  @Override
  protected void processSystem() {
    // No-op
  }
}
