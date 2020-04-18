package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.Target;
import com.dcg.targetsource.SourceEntityAndInputs;

public class Block extends AbstractCommandBuilder {
  protected ComponentMapper<Unit> mUnit;

  public Block() {
    setTargetSource(new SourceEntityAndInputs());
    addTargetConditions(
        target -> target.get().size() == 2,
        target -> coreSystem.getDefendingEntities().anyMatch(e -> e == getDefendingEntity(target)),
        target -> coreSystem.getAttackingEntities().anyMatch(e -> e == getAttackingEntity(target)),
        target -> !mUnit.get(getAttackingEntity(target)).unblockable,
        target ->
            !mUnit.get(getAttackingEntity(target)).flying
                || mUnit.get(getDefendingEntity(target)).flying,
        target ->
            mUnit.get(getDefendingEntity(target)).strength
                >= mUnit.get(getAttackingEntity(target)).strength);
  }

  private int getDefendingEntity(Target target) {
    return target.get().get(0);
  }

  private int getAttackingEntity(Target target) {
    return target.get().get(1);
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
