package com.dcg.util;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.BaseSystem;
import com.artemis.utils.IntBag;
import java.util.ArrayList;
import java.util.List;

public class AspectSystem extends BaseSystem {
  AspectSubscriptionManager manager;

  public List<Integer> get(Aspect.Builder builder) {
    // Slower than raw iteration since we are creating a new list per get() but clarity beats
    // performance for our use cases.
    List<Integer> result = new ArrayList<>();
    IntBag bag = manager.get(builder).getEntities();
    for (int i = 0; i < bag.size(); i++) {
      result.add(bag.get(i));
    }
    return result;
  }

  @Override
  protected void processSystem() {
    // No-op
  }
}
