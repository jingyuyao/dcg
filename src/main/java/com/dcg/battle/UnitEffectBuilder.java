package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.dcg.effect.AbstractEffectBuilder;
import java.util.List;
import java.util.stream.Stream;

abstract class UnitEffectBuilder extends AbstractEffectBuilder {
  protected ComponentMapper<Unit> mUnit;

  protected Stream<Unit> getUnits(List<Integer> targets) {
    return targets.stream().map(mUnit::get);
  }
}
