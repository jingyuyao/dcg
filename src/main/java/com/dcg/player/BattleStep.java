package com.dcg.player;

import com.artemis.ComponentMapper;
import com.dcg.battle.Attacking;
import com.dcg.battle.Defending;
import com.dcg.battle.DestroyUnit;
import com.dcg.battle.Unit;
import com.dcg.command.AbstractCommandBuilder;
import java.util.List;

public class BattleStep extends AbstractCommandBuilder {
  protected ComponentMapper<Unit> mUnit;
  protected ComponentMapper<Defending> mDefending;
  protected ComponentMapper<Attacking> mAttacking;

  @Override
  protected void run(int originEntity, List<Integer> targets, int value) {
    coreSystem
        .getAttackingEntities()
        .forEach(attackingUnitEntity -> attack(attackingUnitEntity, originEntity));
    coreSystem.getDefendingEntities().forEach(this::becomeAttack);
    commandChain.addEnd(new CleanUpStep().build(world, originEntity));
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
