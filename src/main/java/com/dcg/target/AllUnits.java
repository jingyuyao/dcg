package com.dcg.target;

import com.artemis.Aspect;
import com.dcg.battle.Unit;
import com.dcg.game.CoreSystem;
import java.util.stream.Stream;

public class AllUnits extends TargetSource {
  protected CoreSystem coreSystem;

  @Override
  protected Stream<Integer> getSource(int originEntity) {
    return coreSystem.getStream(Aspect.all(Unit.class));
  }
}
