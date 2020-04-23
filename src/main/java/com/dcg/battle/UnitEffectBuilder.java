package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.dcg.effect.AbstractEffectBuilder;
import com.dcg.target.Target;
import java.util.stream.Stream;

abstract class UnitEffectBuilder extends AbstractEffectBuilder {
  protected ComponentMapper<Unit> mUnit;

  protected Stream<Unit> getUnits(Target target) {
    return target.getTargets().stream().map(mUnit::get);
  }
}
