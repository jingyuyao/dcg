package com.dcg.targetsource;

import com.artemis.ComponentMapper;
import com.dcg.battle.Unit;
import com.dcg.game.CoreSystem;
import java.util.List;
import java.util.stream.Collectors;

public class DefendingMaxStrength implements TargetSource {
  private final int strength;
  protected CoreSystem coreSystem;
  protected ComponentMapper<Unit> mUnit;

  public DefendingMaxStrength(int strength) {
    this.strength = strength;
  }

  @Override
  public List<Integer> get(int sourceEntity, List<Integer> input) {
    return coreSystem
        .getDefendingEntities()
        .filter(unitEntity -> mUnit.get(unitEntity).strength <= strength)
        .boxed()
        .collect(Collectors.toList());
  }
}
