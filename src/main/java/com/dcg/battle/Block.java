package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.dcg.command.Command;
import java.util.List;

public class Block extends Command {
  private final int blockingEntity;
  protected World world;
  protected ComponentMapper<Unit> mUnit;

  public Block(int blockingEntity) {
    this.blockingEntity = blockingEntity;
  }

  @Override
  public boolean canRun() {
    List<Integer> input = getInput();
    if (input.size() != 1) {
      System.out.println("    Block requires one input");
      return false;
    }

    int attackingEntity = input.get(0);

    if (!mUnit.has(attackingEntity)) {
      System.out.printf("    *%d is not a unit\n", attackingEntity);
      return false;
    }

    Unit blockingUnit = mUnit.get(blockingEntity);
    Unit attackingUnit = mUnit.get(attackingEntity);

    if (blockingUnit.strength < attackingUnit.strength) {
      System.out.printf(
          "    Defending strength %d is less than attacking strength %d\n",
          blockingUnit.strength, attackingUnit.strength);
      return false;
    }

    return true;
  }

  @Override
  public void run() {
    List<Integer> input = getInput();
    int attackingEntity = input.get(0);
    world.delete(attackingEntity);
    world.delete(blockingEntity);
  }
}
