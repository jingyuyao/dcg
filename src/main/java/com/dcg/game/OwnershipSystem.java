package com.dcg.game;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import java.util.stream.IntStream;

/**
 * A system that provide methods to query a tree based entity relationship. Also automatically
 * removes child entities when its parent is deleted.
 */
@All(Owned.class)
public class OwnershipSystem extends IteratingSystem {
  protected AspectSystem aspectSystem;
  protected ComponentMapper<Owned> mOwned;

  /** Filters the aspect for entities owned by the owner. */
  public IntStream getOwnedBy(int ownerEntity, Aspect.Builder aspectBuilder) {
    return aspectSystem
        .getStream(aspectBuilder.one(Owned.class))
        .filter(e -> ownerEntity == mOwned.get(e).owner);
  }

  /** Filters the aspect for entities not owned by the owner. */
  public IntStream getNotOwnedBy(int ownerEntity, Aspect.Builder aspectBuilder) {
    return aspectSystem
        .getStream(aspectBuilder.one(Owned.class))
        .filter(e -> ownerEntity != mOwned.get(e).owner);
  }

  /** Filters the aspect for entities with the same owner as the owned entity. */
  public IntStream getPeersOf(int ownedEntity, Aspect.Builder aspectBuilder) {
    return getOwnedBy(getOwner(ownedEntity), aspectBuilder);
  }

  /** Get all descendants owned by the owner matching the aspect. */
  public IntStream getDescendants(int ownerEntity, Aspect.Builder aspectBuilder) {
    IntStream.Builder streamBuilder = IntStream.builder();
    getDescendantsImpl(ownerEntity, aspectBuilder, streamBuilder);
    return streamBuilder.build();
  }

  private void getDescendantsImpl(
      int ownerEntity, Aspect.Builder aspectBuilder, IntStream.Builder accumulator) {
    getOwnedBy(ownerEntity, aspectBuilder).forEach(accumulator::add);
    aspectSystem
        .getStream(Aspect.all(Owned.class))
        .filter(ownedEntity -> ownerEntity == mOwned.get(ownedEntity).owner)
        .forEach(ownedEntity -> getDescendantsImpl(ownedEntity, aspectBuilder, accumulator));
  }

  /** Returns the direct owner of the owned entity or -1 if it does not have an owner. */
  public int getOwner(int ownedEntity) {
    return mOwned.has(ownedEntity) ? mOwned.get(ownedEntity).owner : -1;
  }

  @Override
  protected void process(int ownedEntity) {
    if (mOwned.get(ownedEntity).owner == -1) {
      System.out.printf("    *%d auto deleted\n", ownedEntity);
      world.delete(ownedEntity);
    }
  }
}
