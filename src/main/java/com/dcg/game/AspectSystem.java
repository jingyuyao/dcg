package com.dcg.game;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.BaseSystem;
import com.artemis.utils.IntBag;
import java.util.stream.IntStream;

public class AspectSystem extends BaseSystem {
  protected AspectSubscriptionManager manager;

  /** Get all entities matching the aspect as a stream. */
  public IntStream getStream(Aspect.Builder aspectBuilder) {
    IntStream.Builder streamBuilder = IntStream.builder();
    IntBag bag = manager.get(aspectBuilder).getEntities();
    for (int i = 0; i < bag.size(); i++) {
      streamBuilder.add(bag.get(i));
    }
    return streamBuilder.build();
  }

  @Override
  protected void processSystem() {
    // No-op
  }
}
