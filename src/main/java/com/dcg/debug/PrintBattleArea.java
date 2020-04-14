package com.dcg.debug;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.battle.Unit;
import com.dcg.command.Command;
import com.dcg.ownership.OwnershipSystem;
import com.dcg.turn.TurnSystem;
import java.util.List;

public class PrintBattleArea extends Command {
  protected TurnSystem turnSystem;
  protected OwnershipSystem ownershipSystem;
  protected ComponentMapper<Unit> mUnit;

  @Override
  public void run() {
    int currentPlayerEntity = turnSystem.getCurrentPlayerEntity();
    List<Integer> attacking =
        ownershipSystem.getNotOwnedBy(currentPlayerEntity, Aspect.all(Unit.class));
    List<Integer> defending =
        ownershipSystem.getOwnedBy(currentPlayerEntity, Aspect.all(Unit.class));
    System.out.println("    Attacking");
    for (int entity : attacking) {
      System.out.printf("      *%d %s\n", entity, mUnit.get(entity));
    }
    System.out.println("    Defending");
    for (int entity : defending) {
      System.out.printf("      *%d %s\n", entity, mUnit.get(entity));
    }
  }
}
