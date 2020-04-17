package com.dcg.debug;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.battle.Unit;
import java.util.List;
import java.util.stream.Collectors;

public class PrintUnits extends DebugEntityCommand {;
  protected ComponentMapper<Unit> mUnit;

  @Override
  protected void run() {
    List<Integer> currentPlayerUnits =
        coreSystem
            .getCurrentPlayerEntity()
            .flatMap(
                playerEntity -> coreSystem.getDescendants(playerEntity, Aspect.all(Unit.class)))
            .boxed()
            .collect(Collectors.toList());

    System.out.println("  Attacking");
    coreSystem
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
