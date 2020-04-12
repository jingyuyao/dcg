package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.location.DiscardPile;
import com.dcg.location.MoveLocation;
import com.dcg.ownership.OwnershipSystem;
import com.dcg.turn.TurnSystem;
import java.util.List;

public class Block extends Command {
  @Wire protected CommandChain commandChain;
  protected TurnSystem turnSystem;
  protected OwnershipSystem ownershipSystem;
  protected ComponentMapper<Strength> mStrength;

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

    if (!mStrength.has(blockingEntity) || !mStrength.has(attackingEntity)) {
      System.out.println("    Both entity must have strength");
      return;
    }

    Strength blockingStrength = mStrength.get(blockingEntity);
    Strength attackingStrength = mStrength.get(attackingEntity);

    if (blockingStrength.value < attackingStrength.value) {
      System.out.printf(
          "    Defending strength %d is less than attacking strength %d\n",
          blockingStrength.value, attackingStrength.value);
      return;
    }

    commandChain.addStart(
        new MoveLocation(blockingEntity, DiscardPile.class),
        new MoveLocation(attackingEntity, DiscardPile.class));
  }
}
