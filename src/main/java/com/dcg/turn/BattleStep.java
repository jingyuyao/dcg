package com.dcg.turn;

import static com.dcg.player.AdjustHp.hp;

import com.artemis.ComponentMapper;
import com.dcg.battle.Attacking;
import com.dcg.battle.Defending;
import com.dcg.battle.DestroyUnit;
import com.dcg.battle.Unit;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.Command;
import com.dcg.command.CommandData;

public class BattleStep extends AbstractCommandBuilder {
  protected ComponentMapper<Unit> mUnit;
  protected ComponentMapper<Defending> mDefending;
  protected ComponentMapper<Attacking> mAttacking;

  @Override
  protected void run(CommandData data) {
    int originEntity = data.getOriginEntity();
    coreSystem
        .getAttackingEntities()
        .forEach(attackingUnitEntity -> attack(attackingUnitEntity, originEntity));
    coreSystem.getDefendingEntities().forEach(this::becomeAttack);
    commandChain.addEnd(new CleanUpStep().build(world, originEntity));
  }

  private void attack(int attackingUnitEntity, int defendingPlayerEntity) {
    Unit attackingUnit = mUnit.get(attackingUnitEntity);
    int damage = attackingUnit.berserk ? attackingUnit.strength * 2 : attackingUnit.strength;
    Command damageCommand = hp(-damage).build(world, defendingPlayerEntity);
    commandChain.addEnd(damageCommand);
    commandChain.logExecution(coreSystem.getRoot(attackingUnitEntity), damageCommand);
    if (attackingUnit.lifeSteal) {
      Command healCommand = hp(damage).build(world, attackingUnitEntity);
      commandChain.addEnd(healCommand);
      commandChain.logExecution(coreSystem.getRoot(attackingUnitEntity), healCommand);
    }
    commandChain.addEnd(new DestroyUnit().build(world, attackingUnitEntity));
  }

  private void becomeAttack(int defendingUnitEntity) {
    mDefending.remove(defendingUnitEntity);
    mAttacking.create(defendingUnitEntity);
  }
}
