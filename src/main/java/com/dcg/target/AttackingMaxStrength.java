package com.dcg.target;

import com.artemis.ComponentMapper;
import com.dcg.battle.Unit;
import com.dcg.command.Input;
import com.dcg.game.CoreSystem;
import java.util.List;
import java.util.stream.Collectors;

public class AttackingMaxStrength implements TargetFunction {
  private final int strength;
  protected CoreSystem coreSystem;
  protected ComponentMapper<Unit> mUnit;

  public AttackingMaxStrength(int strength) {
    this.strength = strength;
  }

  @Override
  public Target apply(Integer sourceEntity, Input input) {
    return new Target() {
      @Override
      public int getOrigin() {
        return sourceEntity;
      }

      @Override
      public List<Integer> getTargets() {
        return coreSystem
            .getAttackingEntities()
            .filter(unitEntity -> mUnit.get(unitEntity).strength <= strength)
            .boxed()
            .collect(Collectors.toList());
      }
    };
  }
}
