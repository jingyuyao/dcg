package com.dcg.triggercondition;

import com.artemis.Aspect;
import com.dcg.card.SeekPower;
import com.dcg.game.CoreSystem;
import java.util.List;

public class ThroneActive implements TriggerCondition {
  protected CoreSystem coreSystem;

  @Override
  public boolean test(int originEntity, List<Integer> allowedTargets) {
    return !coreSystem.getStream(Aspect.all(SeekPower.class)).findAny().isPresent();
  }
}
