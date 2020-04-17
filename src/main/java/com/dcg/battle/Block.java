package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;

public class Block extends AbstractCommandBuilder {;
  protected ComponentMapper<Unit> mUnit;

  @Override
  protected boolean isInputValid() {
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

    if (attackingUnit.flying && !blockingUnit.flying) {
      System.out.printf("    *%d has flying but *%d does not\n", attackingEntity, sourceEntity);
      return false;
    }

    if (blockingUnit.strength + blockingUnit.defense < attackingUnit.strength) {
      System.out.printf(
          "    Defending strength %d is less than attacking strength %d\n",
          blockingUnit.strength, attackingUnit.strength);
      return false;
    }

    return true;
  }

  @Override
  protected void run() {
    int attackingEntity = input.get(0);
    commandChain.addEnd(
        new DestroyUnit().build(world, attackingEntity),
        new DestroyUnit().build(world, sourceEntity));
  }
}
