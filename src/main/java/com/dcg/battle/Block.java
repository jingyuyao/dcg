package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.dcg.command.Command;
import java.util.List;

public class Block extends Command {
  ComponentMapper<Attacking> mAttacking;
  ComponentMapper<Blocking> mBlocking;
  ComponentMapper<Blocked> mBlocked;

  @Override
  public void run() {
    List<Integer> args = getArgs();
    if (args.size() != 2) {
      System.out.println("    Invalid args for defend");
      return;
    }

    int blockingEntity = args.get(0);
    int attackingEntity = args.get(1);

    // TODO: either add preconditions
    // TODO: add target-able tag to help with preconditions

    mAttacking.remove(attackingEntity);
    mBlocking.create(blockingEntity).attackingEntity = attackingEntity;
    mBlocked.create(attackingEntity).blockingEntity = blockingEntity;
  }
}
