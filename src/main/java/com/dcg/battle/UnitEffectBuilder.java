package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.target.Target;
import java.util.stream.Stream;
import net.mostlyoriginal.api.utils.Preconditions;

/**
 * Base command to make changes to an unit. Auto executes if the source entity is a unit, otherwise
 * accepts a single input
 */
abstract class UnitEffectBuilder extends AbstractCommandBuilder {
  protected ComponentMapper<Unit> mUnit;

  UnitEffectBuilder() {
    addTargetConditions(
        targets ->
            Preconditions.checkArgument(
                targets.getTargets().stream().allMatch(mUnit::has), "Target must be a unit"));
  }

  protected Stream<Unit> getUnits(Target target) {
    return target.getTargets().stream().map(mUnit::get);
  }
}
