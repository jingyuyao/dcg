package com.dcg.debug;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.battle.Unit;
import com.dcg.game.AspectSystem;
import com.dcg.ownership.OwnershipSystem;
import com.dcg.turn.TurnSystem;
import java.util.List;
import java.util.stream.Collectors;

public class PrintUnits extends DebugEntityCommand {
  protected TurnSystem turnSystem;
  protected AspectSystem aspectSystem;
  protected OwnershipSystem ownershipSystem;
  protected ComponentMapper<Unit> mUnit;

  @Override
  protected void run() {
    List<Integer> currentPlayerUnits =
        ownershipSystem
            .getDescendants(turnSystem.getPlayerEntity(), Aspect.all(Unit.class))
            .boxed()
            .collect(Collectors.toList());

    System.out.println("  Attacking");
    aspectSystem
        .getStream(Aspect.all(Unit.class))
        .filter(unitEntity -> !currentPlayerUnits.contains(unitEntity))
        .forEach(unitEntity -> printUnit(unitEntity, false));
    System.out.println("  Defending");
    currentPlayerUnits.forEach(actionEntity -> printUnit(actionEntity, true));
  }

  private void printUnit(int unitEntity, boolean withActions) {
    System.out.printf("    *%d %s\n", unitEntity, mUnit.get(unitEntity));
    if (withActions) {
      printActions(unitEntity);
    }
  }
}
