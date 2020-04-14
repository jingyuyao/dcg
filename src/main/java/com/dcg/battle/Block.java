package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.dcg.command.Command;
import com.dcg.ownership.OwnershipSystem;
import com.dcg.turn.TurnSystem;
import java.util.List;

public class Block extends Command {
  protected World world;
  protected TurnSystem turnSystem;
  protected OwnershipSystem ownershipSystem;
  protected ComponentMapper<Unit> mUnit;

  @Override
  public void run() {
    // TODO: add target-able tag to help with preconditions
    List<Integer> targets = getTargetEntities();
    if (targets.size() != 2) {
      System.out.println("    Invalid targets for defend");
      return;
    }

    int blockingEntity = targets.get(0);
    int attackingEntity = targets.get(1);

    if (!ownershipSystem.isOwnedBy(turnSystem.getCurrentPlayerEntity(), blockingEntity)) {
      System.out.printf("    *%d is not owned by the current player\n", blockingEntity);
      return;
    }

    if (!mUnit.has(blockingEntity) || !mUnit.has(attackingEntity)) {
      System.out.println("    Both entity must be units");
      return;
    }

    Unit blockingUnit = mUnit.get(blockingEntity);
    Unit attackingUnit = mUnit.get(attackingEntity);

    if (blockingUnit.strength < attackingUnit.strength) {
      System.out.printf(
          "    Defending strength %d is less than attacking strength %d\n",
          blockingUnit.strength, attackingUnit.strength);
      return;
    }

    world.delete(attackingEntity);
    world.delete(blockingEntity);
  }
}
