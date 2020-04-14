package com.dcg.ownership;

import com.artemis.Aspect.Builder;
import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.dcg.util.AspectSystem;
import java.util.List;
import java.util.stream.Collectors;

public class OwnershipSystem extends BaseSystem {
  protected AspectSystem aspectSystem;
  protected ComponentMapper<Owned> mOwned;

  /** Filters the aspect for entities owned by the owner. */
  public List<Integer> getOwnedBy(int ownerEntity, Builder aspectBuilder) {
    return aspectSystem.get(aspectBuilder.one(Owned.class)).stream()
        .filter(e -> ownerEntity == mOwned.get(e).owner)
        .collect(Collectors.toList());
  }

  /** Filters the aspect for entities not owned by the owner. */
  public List<Integer> getNotOwnedBy(int ownerEntity, Builder aspectBuilder) {
    return aspectSystem.get(aspectBuilder.one(Owned.class)).stream()
        .filter(e -> ownerEntity != mOwned.get(e).owner)
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
