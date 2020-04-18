package com.dcg.debug;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.battle.Unit;
import java.util.List;

public class PrintUnits extends DebugEntityCommand {;
  protected ComponentMapper<Unit> mUnit;

  @Override
  protected void run(List<Integer> input) {
    coreSystem
        .getCurrentPlayerEntity()
        .forEach(
            playerEntity -> {
              System.out.println("  Attacking");
              coreSystem
                  .getNotDescendants(playerEntity, Aspect.all(Unit.class))
                  .forEach(unitEntity -> printUnit(unitEntity, false));
              System.out.println("  Defending");
              coreSystem
                  .getDescendants(playerEntity, Aspect.all(Unit.class))
                  .forEach(unitEntity -> printUnit(unitEntity, true));
            });
  }

  private void printUnit(int unitEntity, boolean withActions) {
    System.out.printf("    *%d %s\n", unitEntity, mUnit.get(unitEntity));
    if (withActions) {
      printActions(unitEntity);
    }
  }
}
