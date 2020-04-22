package com.dcg.game;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.utils.IntBag;
import com.dcg.battle.Attacking;
import com.dcg.battle.Defending;
import com.dcg.battle.Unit;
import com.dcg.location.PlayArea;
import com.dcg.player.Player;
import com.dcg.player.Turn;
import java.util.stream.Collector;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Manages auto deletion of owned entities when their parent is deleted. Provides methods to query
 * entities as streams.
 */
public class CoreSystem extends BaseSystem {
  private static final Common DEFAULT_COMMON = new Common();
  protected AspectSubscriptionManager manager;
  protected ComponentMapper<Common> mNamed;
  protected ComponentMapper<Owned> mOwned;

  // TODO: Don't use IntStream since we are operating on ids rather than true numerical values.

  /** Get all entities matching the aspect as a stream. */
  public IntStream getStream(Aspect.Builder aspectBuilder) {
    IntStream.Builder streamBuilder = IntStream.builder();
    IntBag bag = manager.get(aspectBuilder).getEntities();
    for (int i = 0; i < bag.size(); i++) {
      streamBuilder.add(bag.get(i));
    }
    return streamBuilder.build();
  }

  public IntStream findByName(String name, Aspect.Builder aspectBuilder) {
    return getStream(aspectBuilder.all(Common.class))
        .filter(entity -> name.equalsIgnoreCase(mNamed.get(entity).name));
  }

  public String toName(int entity) {
    return entity != -1 ? mNamed.getSafe(entity, DEFAULT_COMMON).name : "";
  }

  public IntStream getCurrentPlayerEntity() {
    return getStream(Aspect.all(Player.class, Turn.class));
  }

  public IntStream getAttackingEntities() {
    return getStream(Aspect.all(Unit.class, Attacking.class));
  }

  public IntStream getDefendingEntities() {
    return getStream(Aspect.all(Unit.class, Defending.class));
  }

  public Stream<Integer> getPlayArea() {
    return getStream(Aspect.all(PlayArea.class)).boxed();
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
    return getStream(aspectBuilder.one(Owned.class))
        .filter(entity -> isOwnedBy(ownerEntity, entity));
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

  public boolean isOwnedBy(int ownerEntity, int entity) {
    int parent = getParent(entity);
    return parent == ownerEntity || (mOwned.has(parent) && isOwnedBy(ownerEntity, parent));
  }

  public static Collector<Integer, IntBag, IntBag> toIntBag() {
    return Collector.of(
        IntBag::new,
        IntBag::add,
        (bag1, bag2) -> {
          if (bag1.size() > bag2.size()) {
            bag1.addAll(bag2);
            return bag1;
          } else {
            bag2.addAll(bag1);
            return bag2;
          }
        });
  }

  @Override
  protected void processSystem() {}
}
