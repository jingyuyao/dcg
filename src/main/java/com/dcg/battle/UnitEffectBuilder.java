package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.dcg.effect.AbstractEffectBuilder;
import java.util.Optional;

/**
 * Base command to make changes to an unit. Auto executes if the source entity is a unit, otherwise
 * accepts a single input
 */
abstract class UnitEffectBuilder extends AbstractEffectBuilder<Unit> {
  protected ComponentMapper<Unit> mUnit;

  @Override
  protected Optional<ComponentMapper<Unit>> getComponentMapper() {
    return Optional.of(mUnit);
  }
}
