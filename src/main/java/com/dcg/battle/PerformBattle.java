package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.player.AdjustHp;
import com.dcg.target.Target;

public class PerformBattle extends AbstractCommandBuilder {
  protected ComponentMapper<Unit> mUnit;
  protected ComponentMapper<Defending> mDefending;
  protected ComponentMapper<Attacking> mAttacking;

  @Override
  protected void run(Target target) {
    int defendingPlayerEntity = target.getOrigin();
    coreSystem
        .getAttackingEntities()
        .forEach(attackingUnitEntity -> attack(attackingUnitEntity, defendingPlayerEntity));
    coreSystem.getDefendingEntities().forEach(this::becomeAttack);
  }

  private void attack(int attackingUnitEntity, int defendingPlayerEntity) {
    Unit attackingUnit = mUnit.get(attackingUnitEntity);
    int damage = attackingUnit.berserk ? attackingUnit.strength * 2 : attackingUnit.strength;
    commandChain.addEnd(new AdjustHp(-damage).build(world, defendingPlayerEntity));
    if (attackingUnit.lifeSteal) {
      commandChain.addEnd(new AdjustHp(damage).build(world, attackingUnitEntity));
    }
    commandChain.addEnd(new DestroyUnit().build(world, attackingUnitEntity));
  }

  private void becomeAttack(int defendingUnitEntity) {
    mDefending.remove(defendingUnitEntity);
    mAttacking.create(defendingUnitEntity);
  }
}
