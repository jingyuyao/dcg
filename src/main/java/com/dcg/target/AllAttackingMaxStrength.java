package com.dcg.target;

import com.artemis.ComponentMapper;
import com.dcg.battle.Unit;
import com.dcg.game.CoreSystem;
import java.util.List;
import java.util.stream.Collectors;

public class AllAttackingMaxStrength extends TargetSource {
  private final int strength;
  protected CoreSystem coreSystem;
  protected ComponentMapper<Unit> mUnit;

  public AllAttackingMaxStrength(int strength) {
    this.strength = strength;
  }

  @Override
  protected List<Integer> transform(int originEntity, List<Integer> input) {
    return coreSystem
        .getAttackingEntities()
        .filter(unitEntity -> mUnit.get(unitEntity).strength <= strength)
        .collect(Collectors.toList());
  }
}
