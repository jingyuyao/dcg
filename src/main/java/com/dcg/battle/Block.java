package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.game.CoreSystem;
import com.dcg.target.AttackingUnits;
import com.dcg.target.Target;
import com.dcg.target.TargetFilter;
import java.util.stream.Stream;

public class Block extends AbstractCommandBuilder {
  protected ComponentMapper<Unit> mUnit;

  public Block() {
    setTargetSource(new AttackingUnits().addFilters(new BlockPredicate()));
    setTargetCount(1);
  }

  private int getDefendingEntity(Target target) {
    return target.getOrigin();
  }

  private int getAttackingEntity(Target target) {
    return target.getTargets().get(0);
  }

  @Override
  protected void run(Target target) {
    commandChain.addEnd(new DestroyUnit().build(world, getAttackingEntity(target)));
    Unit blockingUnit = mUnit.get(getDefendingEntity(target));
    if (!blockingUnit.endurance) {
      commandChain.addEnd(new DestroyUnit().build(world, getDefendingEntity(target)));
    }
  }

  private static class BlockPredicate implements TargetFilter {
    protected CoreSystem coreSystem;
    protected ComponentMapper<Unit> mUnit;

    @Override
    public Stream<Integer> apply(Integer originEntity, Stream<Integer> source) {
      Unit defendingUnit = mUnit.get(originEntity);
      return source.filter(
          attackingEntity -> {
            Unit attackingUnit = mUnit.get(attackingEntity);
            return !attackingUnit.unblockable
                && (!attackingUnit.flying || defendingUnit.flying)
                && defendingUnit.strength + defendingUnit.defense >= attackingUnit.strength;
          });
    }
  }
}
