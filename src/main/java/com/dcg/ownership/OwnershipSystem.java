package com.dcg.ownership;

import com.artemis.Aspect.Builder;
import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.dcg.util.AspectSystem;
import java.util.List;
import java.util.stream.Collectors;

@All(Owned.class)
public class OwnershipSystem extends IteratingSystem {
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

  /** Filters the aspect for entities with the same owner as the owned entity. */
  public List<Integer> getPeersOf(int ownedEntity, Builder aspectBuilder) {
    return getOwnedBy(getOwner(ownedEntity), aspectBuilder);
  }

  public int getOwner(int ownedEntity) {
    return mOwned.has(ownedEntity) ? mOwned.get(ownedEntity).owner : -1;
  }

  /** Returns whether the owner owns the entity. Parent relationships are checked as well. */
  public boolean isOwnedBy(int ownerEntity, int ownedEntity) {
    if (!mOwned.has(ownedEntity)) {
      return false;
    }
    Owned owned = mOwned.get(ownedEntity);
    if (owned.owner == ownerEntity) {
      return true;
    }
    if (owned.owner != -1) {
      return isOwnedBy(ownerEntity, owned.owner);
    }
    return false;
  }

  @Override
  protected void process(int ownedEntity) {
    if (mOwned.get(ownedEntity).owner == -1) {
      System.out.printf("    *%d auto deleted\n", ownedEntity);
      world.delete(ownedEntity);
    }
  }
}
