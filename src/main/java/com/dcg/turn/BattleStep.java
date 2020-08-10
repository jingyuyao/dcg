package com.dcg.turn;

import static com.dcg.battle.DestroyUnit.autoDestroyUnit;
import static com.dcg.player.AdjustHp.hp;

import com.artemis.ComponentMapper;
import com.dcg.battle.Attacking;
import com.dcg.battle.Defending;
import com.dcg.battle.Unit;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.Command;
import com.dcg.command.CommandData;
import com.dcg.targetsource.TargetSource;

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
    Command damageCommand =
        hp(-damage)
            .setTargetSource(TargetSource.of(defendingPlayerEntity))
            .build(world, attackingUnitEntity);
    commandChain.addEnd(damageCommand);
    if (attackingUnit.lifeSteal) {
      int attackingPlayerEntity = coreSystem.getRoot(attackingUnitEntity);
      Command healCommand =
          hp(damage)
              .setTargetSource(TargetSource.of(attackingPlayerEntity))
              .build(world, attackingUnitEntity);
      commandChain.addEnd(healCommand);
    }
    commandChain.addEnd(autoDestroyUnit().build(world, attackingUnitEntity));
  }

  private void becomeAttack(int defendingUnitEntity) {
    mDefending.remove(defendingUnitEntity);
    mAttacking.create(defendingUnitEntity);
  }
}
