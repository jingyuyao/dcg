package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import java.util.List;
import java.util.stream.Stream;

/**
 * Base command to make changes to an unit. Auto executes if the source entity is a unit, otherwise
 * accepts a single input
 */
abstract class UnitEffectBuilder extends AbstractCommandBuilder {
  protected ComponentMapper<Unit> mUnit;

  UnitEffectBuilder() {
    addTargetConditions(targets -> targets.stream().allMatch(mUnit::has));
  }

  protected Stream<Unit> getUnits(List<Integer> input) {
    return getTargetEntities(input).stream().map(mUnit::get);
  }
}
