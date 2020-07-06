package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandData;
import com.dcg.game.CoreSystem;
import com.dcg.targetfilter.TargetFilter;
import com.dcg.targetsource.AttackingUnits;
import com.dcg.triggercondition.MinAllowedTargets;

public class Block extends AbstractCommandBuilder {
  protected ComponentMapper<Unit> mUnit;

  public Block() {
    addTriggerConditions(new MinAllowedTargets(1));
    setTargetSource(new AttackingUnits().addFilters(new BlockPredicate()));
    setInputCount(1);
  }

  @Override
  protected void run(CommandData data) {
    int originEntity = data.getOriginEntity();
    commandChain.addEnd(new DestroyUnit().build(world, data.getTargets().get(0)));
    Unit blockingUnit = mUnit.get(originEntity);
    if (!blockingUnit.endurance) {
      commandChain.addEnd(new DestroyUnit().build(world, originEntity));
    }
  }

  private static class BlockPredicate implements TargetFilter {
    protected CoreSystem coreSystem;
    protected ComponentMapper<Unit> mUnit;

    @Override
    public boolean test(int originEntity, int target) {
      Unit defendingUnit = mUnit.get(originEntity);
      Unit attackingUnit = mUnit.get(target);
      return !attackingUnit.unblockable
          && (!attackingUnit.flying || defendingUnit.flying)
          && defendingUnit.strength + defendingUnit.defense >= attackingUnit.strength;
    }
  }
}
