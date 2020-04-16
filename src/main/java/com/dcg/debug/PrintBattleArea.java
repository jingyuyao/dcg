package com.dcg.debug;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.battle.Unit;
import com.dcg.ownership.OwnershipSystem;
import com.dcg.turn.TurnSystem;

public class PrintBattleArea extends DebugEntityCommand {
  protected TurnSystem turnSystem;
  protected OwnershipSystem ownershipSystem;
  protected ComponentMapper<Unit> mUnit;

  @Override
  protected void run() {
    int currentPlayerEntity = turnSystem.getPlayerEntity();
    System.out.println("  Attacking");
    ownershipSystem
        .getNotOwnedBy(currentPlayerEntity, Aspect.all(Unit.class))
        .forEach(actionEntity -> printUnit(actionEntity, false));
    System.out.println("  Defending");
    ownershipSystem
        .getOwnedBy(currentPlayerEntity, Aspect.all(Unit.class))
        .forEach(actionEntity -> printUnit(actionEntity, true));
  }

  private void printUnit(int unitEntity, boolean withActions) {
    System.out.printf("    *%d %s\n", unitEntity, mUnit.get(unitEntity));
    if (withActions) {
      printActions(unitEntity);
    }
  }
}
