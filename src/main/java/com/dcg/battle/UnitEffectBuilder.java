package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.Target;
import java.util.stream.Stream;

/**
 * Base command to make changes to an unit. Auto executes if the source entity is a unit, otherwise
 * accepts a single input
 */
abstract class UnitEffectBuilder extends AbstractCommandBuilder {
  protected ComponentMapper<Unit> mUnit;

  UnitEffectBuilder() {
    addTargetConditions(targets -> targets.get().stream().allMatch(mUnit::has));
  }

  protected Stream<Unit> getUnits(Target target) {
    return target.get().stream().map(mUnit::get);
  }
}
