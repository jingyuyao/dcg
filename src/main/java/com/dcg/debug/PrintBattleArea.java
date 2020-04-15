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
  public void run() {
    int currentPlayerEntity = turnSystem.getCurrentPlayerEntity();
    System.out.println("    Attacking");
    ownershipSystem
        .getNotOwnedBy(currentPlayerEntity, Aspect.all(Unit.class))
        .forEach(this::printUnit);
    System.out.println("    Defending");
    ownershipSystem
        .getOwnedBy(currentPlayerEntity, Aspect.all(Unit.class))
        .forEach(this::printUnit);
  }

  private void printUnit(int unitEntity) {
    System.out.printf("      *%d %s\n", unitEntity, mUnit.get(unitEntity));
    printActions(unitEntity);
  }
}
