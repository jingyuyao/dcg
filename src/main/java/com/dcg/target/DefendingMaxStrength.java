package com.dcg.target;

import com.artemis.ComponentMapper;
import com.dcg.battle.Unit;
import com.dcg.game.CoreSystem;
import java.util.List;
import java.util.stream.Collectors;

public class DefendingMaxStrength implements TargetFunction {
  private final int strength;
  protected CoreSystem coreSystem;
  protected ComponentMapper<Unit> mUnit;

  public DefendingMaxStrength(int strength) {
    this.strength = strength;
  }

  @Override
  public Target apply(Integer originEntity, List<Integer> inputs) {
    return new Target() {
      @Override
      public int getOrigin() {
        return originEntity;
      }

      @Override
      public List<Integer> getTargets() {
        return coreSystem
            .getDefendingEntities()
            .filter(unitEntity -> mUnit.get(unitEntity).strength <= strength)
            .boxed()
            .collect(Collectors.toList());
      }
    };
  }
}
