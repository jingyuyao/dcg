package com.dcg.battle;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.Target;
import com.dcg.player.AdjustHp;

public class PerformBattle extends AbstractCommandBuilder {;
  protected ComponentMapper<Unit> mUnit;

  @Override
  protected void run(Target target) {
    int defendingPlayerEntity = target.get().get(0);
    coreSystem
        .getNotDescendants(defendingPlayerEntity, Aspect.all(Unit.class))
        .forEach(attackingUnitEntity -> attack(attackingUnitEntity, defendingPlayerEntity));
  }

  private void attack(int attackingUnitEntity, int defendingPlayerEntity) {
    Unit attackingUnit = mUnit.get(attackingUnitEntity);
    int damage = attackingUnit.berserk ? attackingUnit.strength : attackingUnit.strength * 2;
    commandChain.addEnd(new AdjustHp(-damage).build(world, defendingPlayerEntity));
    if (attackingUnit.lifeSteal) {
      commandChain.addEnd(new AdjustHp(damage).build(world, attackingUnitEntity));
    }
    commandChain.addEnd(new DestroyUnit().build(world, attackingUnitEntity));
  }
}
