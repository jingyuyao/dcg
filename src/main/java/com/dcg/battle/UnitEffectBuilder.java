package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import java.util.List;
import java.util.stream.Stream;

abstract class UnitEffectBuilder extends AbstractCommandBuilder {
  protected ComponentMapper<Unit> mUnit;

  protected Stream<Unit> getUnits(List<Integer> targets) {
    return targets.stream().map(mUnit::get);
  }
}
