package com.dcg.game;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.artemis.utils.IntBag;
import com.dcg.player.Player;
import com.dcg.player.Turn;
import java.util.stream.IntStream;

/**
 * Manages auto deletion of owned entities when their parent is deleted. Provides methods to query
 * entities as streams.
 */
@All(Owned.class)
public class CoreSystem extends IteratingSystem {
  protected AspectSubscriptionManager manager;
  protected ComponentMapper<Owned> mOwned;

  /** Get all entities matching the aspect as a stream. */
  public IntStream getStream(Aspect.Builder aspectBuilder) {
    IntStream.Builder streamBuilder = IntStream.builder();
    IntBag bag = manager.get(aspectBuilder).getEntities();
    for (int i = 0; i < bag.size(); i++) {
      streamBuilder.add(bag.get(i));
    }
    return streamBuilder.build();
  }

  public IntStream getCurrentPlayerEntity() {
    return getStream(Aspect.all(Player.class, Turn.class));
  }

  /** Filters the aspect for entities owned by the owner. */
  public IntStream getChildren(int ownerEntity, Aspect.Builder aspectBuilder) {
    return getStream(aspectBuilder.one(Owned.class))
        .filter(e -> ownerEntity == mOwned.get(e).owner);
  }

  /** Filters the aspect for entities not owned by the owner. */
  public IntStream getNotChildren(int ownerEntity, Aspect.Builder aspectBuilder) {
    return getStream(aspectBuilder.one(Owned.class))
        .filter(e -> ownerEntity != mOwned.get(e).owner);
  }

  /** Filters the aspect for entities with the same owner as the owned entity. */
  public IntStream getPeers(int ownedEntity, Aspect.Builder aspectBuilder) {
    return getChildren(getParent(ownedEntity), aspectBuilder);
  }

  /**
   * Get all descendants owned by the owner matching the aspect. Intermediate nodes does not need to
   * match the filter.
   */
  public IntStream getDescendants(int ownerEntity, Aspect.Builder aspectBuilder) {
    IntStream.Builder streamBuilder = IntStream.builder();
    getDescendantsImpl(ownerEntity, aspectBuilder, streamBuilder);
    return streamBuilder.build();
  }

  private void getDescendantsImpl(
      int ownerEntity, Aspect.Builder aspectBuilder, IntStream.Builder accumulator) {
    getChildren(ownerEntity, aspectBuilder).forEach(accumulator::add);
    getChildren(ownerEntity, Aspect.all())
        .forEach(ownedEntity -> getDescendantsImpl(ownedEntity, aspectBuilder, accumulator));
  }

  /** Get all entities not owned by the owner matching the aspect. */
  public IntStream getNotDescendants(int ownerEntity, Aspect.Builder aspectBuilder) {
    return getStream(aspectBuilder).filter(entity -> getRoot(entity) != ownerEntity);
  }

  /** Returns the direct owner of the owned entity or -1 if it does not have an owner. */
  public int getParent(int ownedEntity) {
    return mOwned.has(ownedEntity) ? mOwned.get(ownedEntity).owner : -1;
  }

  /** Returns the root owner of the entity or itself if it does not have an owner. */
  public int getRoot(int entity) {
    return mOwned.has(entity) ? getRoot(mOwned.get(entity).owner) : entity;
  }

  @Override
  protected void process(int ownedEntity) {
    if (mOwned.get(ownedEntity).owner == -1) {
      System.out.printf("    *%d auto deleted\n", ownedEntity);
      world.delete(ownedEntity);
    }
  }
}
