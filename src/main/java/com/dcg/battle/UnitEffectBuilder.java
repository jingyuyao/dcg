package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandData;
import java.util.List;
import java.util.stream.Stream;

abstract class UnitEffectBuilder extends AbstractCommandBuilder {
  protected ComponentMapper<Unit> mUnit;

  protected Stream<Unit> getUnits(List<Integer> targets) {
    return targets.stream().map(mUnit::get);
  }

  protected abstract String getKeyword();

  @Override
  protected String getDescription(CommandData data) {
    String targetNames = coreSystem.toNames(data.getOriginEntity(), data.getTargets());
    if (data.getInt() != 0) {
      int value = data.getInt();
      String mod = value > 0 ? "+" : "-";
      return String.format("%s%d %s to %s", mod, value, getKeyword(), targetNames);
    } else {
      String mod = data.getBool() ? "grants" : "removes";
      return String.format("%s %s to %s", mod, getKeyword(), targetNames);
    }
  }

  @Override
  protected boolean isClientVisible(CommandData data) {
    // TODO: return false for static properties (e.g. units that always have flying by default)
    return true;
  }
}
