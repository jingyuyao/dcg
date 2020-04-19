package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.Target;
import com.dcg.source.Inputs;

public class Block extends AbstractCommandBuilder {
  protected ComponentMapper<Unit> mUnit;

  public Block() {
    setTargetFunction(new Inputs());
    addTargetConditions(
        target -> target.getTo().size() == 1,
        target -> coreSystem.getDefendingEntities().anyMatch(e -> e == getDefendingEntity(target)),
        target -> coreSystem.getAttackingEntities().anyMatch(e -> e == getAttackingEntity(target)),
        target -> !mUnit.get(getAttackingEntity(target)).unblockable,
        target ->
            !mUnit.get(getAttackingEntity(target)).flying
                || mUnit.get(getDefendingEntity(target)).flying,
        target -> {
          Unit defendingUnit = mUnit.get(getDefendingEntity(target));
          return defendingUnit.strength + defendingUnit.defense
              >= mUnit.get(getAttackingEntity(target)).strength;
        });
  }

  private int getDefendingEntity(Target target) {
    return target.getFrom();
  }

  private int getAttackingEntity(Target target) {
    return target.getTo().get(0);
  }

  @Override
  protected void run(Target target) {
    commandChain.addEnd(new DestroyUnit().build(world, getAttackingEntity(target)));
    Unit blockingUnit = mUnit.get(getDefendingEntity(target));
    if (!blockingUnit.endurance) {
      commandChain.addEnd(new DestroyUnit().build(world, getDefendingEntity(target)));
    }
  }
}
