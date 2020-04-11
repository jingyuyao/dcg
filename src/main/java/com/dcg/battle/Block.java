package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.dcg.command.Command;
import java.util.List;

public class Block extends Command {
  ComponentMapper<Blocking> mBlocking;

  @Override
  public void run() {
    List<Integer> args = getArgs();
    if (args.size() != 2) {
      System.out.println("    Invalid args for defend");
      return;
    }

    int defenderEntity = args.get(0);
    int attackerEntity = args.get(1);

    mBlocking.create(defenderEntity).attackingEntity = attackerEntity;
  }
}
