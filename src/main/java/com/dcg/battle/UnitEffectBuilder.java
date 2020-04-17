package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.dcg.effect.AbstractEffectBuilder;

/**
 * Base command to make changes to an unit. Auto executes if the source entity is a unit, otherwise
 * accepts a single input
 */
abstract class UnitEffectBuilder extends AbstractEffectBuilder {
  protected ComponentMapper<Unit> mUnit;

  @Override
  protected boolean isTargetEntityValid(int targetEntity) {
    return mUnit.has(targetEntity);
  }
}
