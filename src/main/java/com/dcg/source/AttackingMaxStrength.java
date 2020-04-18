package com.dcg.source;

import com.artemis.ComponentMapper;
import com.dcg.battle.Unit;
import com.dcg.command.Input;
import com.dcg.command.Target;
import com.dcg.game.CoreSystem;
import java.util.stream.Collectors;

public class AttackingMaxStrength implements TargetSource {
  private final int strength;
  protected CoreSystem coreSystem;
  protected ComponentMapper<Unit> mUnit;

  public AttackingMaxStrength(int strength) {
    this.strength = strength;
  }

  @Override
  public Target apply(Integer integer, Input input) {
    return () ->
        coreSystem
            .getAttackingEntities()
            .filter(unitEntity -> mUnit.get(unitEntity).strength <= strength)
            .boxed()
            .collect(Collectors.toList());
  }
}
