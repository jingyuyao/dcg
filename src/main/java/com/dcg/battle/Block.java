package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.targetsource.Inputs;
import java.util.List;

public class Block extends AbstractCommandBuilder {;
  protected ComponentMapper<Unit> mUnit;

  public Block() {
    setTargetSource(new Inputs());
    addTargetConditions(
        input -> {
          if (input.size() != 1) {
            System.out.println("    Block requires one input");
            return false;
          }

          int attackingEntity = input.get(0);

          if (!mUnit.has(attackingEntity)) {
            System.out.printf("    *%d is not a unit\n", attackingEntity);
            return false;
          }

          if (coreSystem.getParent(sourceEntity) == coreSystem.getParent(attackingEntity)) {
            System.out.println("    Can't block your own units");
            return false;
          }

          Unit blockingUnit = mUnit.get(sourceEntity);
          Unit attackingUnit = mUnit.get(attackingEntity);

          if (attackingUnit.unblockable) {
            System.out.printf("    %s is unblockable\n", attackingUnit);
            return false;
          }

          if (attackingUnit.flying && !blockingUnit.flying) {
            System.out.printf("    %s has flying but %s does not\n", attackingUnit, blockingUnit);
            return false;
          }

          if (blockingUnit.strength + blockingUnit.defense < attackingUnit.strength) {
            System.out.printf("    %s has less strength than %s\n", blockingUnit, attackingUnit);
            return false;
          }

          return true;
        });
  }

  @Override
  protected void run(List<Integer> input) {
    int attackingEntity = input.get(0);
    commandChain.addEnd(new DestroyUnit().build(world, attackingEntity));
    Unit blockingUnit = mUnit.get(sourceEntity);
    if (!blockingUnit.endurance) {
      commandChain.addEnd(new DestroyUnit().build(world, sourceEntity));
    }
  }
}
