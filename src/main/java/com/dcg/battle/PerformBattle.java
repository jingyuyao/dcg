package com.dcg.battle;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.player.AdjustHp;

public class PerformBattle extends AbstractCommandBuilder {;
  protected ComponentMapper<Unit> mUnit;

  @Override
  protected void run() {
    coreSystem.getNotDescendants(sourceEntity, Aspect.all(Unit.class)).forEach(this::attack);
  }

  private void attack(int attackingUnitEntity) {
    Unit attackingUnit = mUnit.get(attackingUnitEntity);
    int damage = attackingUnit.berserk ? attackingUnit.strength : attackingUnit.strength * 2;
    commandChain.addEnd(new AdjustHp(-damage, true).build(world, sourceEntity));
    if (attackingUnit.lifeSteal) {
      commandChain.addEnd(new AdjustHp(damage, true).build(world, attackingUnitEntity));
    }
    commandChain.addEnd(new DestroyUnit().build(world, attackingUnitEntity));
  }
}
