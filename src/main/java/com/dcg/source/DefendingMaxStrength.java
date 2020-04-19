package com.dcg.source;

import com.artemis.ComponentMapper;
import com.dcg.battle.Unit;
import com.dcg.command.Input;
import com.dcg.command.Target;
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
  public Target apply(Integer sourceEntity, Input input) {
    return new Target() {
      @Override
      public int getFrom() {
        return sourceEntity;
      }

      @Override
      public List<Integer> getTo() {
        return coreSystem
            .getDefendingEntities()
            .filter(unitEntity -> mUnit.get(unitEntity).strength <= strength)
            .boxed()
            .collect(Collectors.toList());
      }
    };
  }
}
