package com.dcg.debug;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.battle.Unit;
import com.dcg.command.Target;

public class PrintUnits extends DebugEntityCommand {
  protected ComponentMapper<Unit> mUnit;

  @Override
  protected void run(Target target) {
    coreSystem
        .getCurrentPlayerEntity()
        .forEach(
            playerEntity -> {
              System.out.println("    Attacking");
              coreSystem
                  .getNotDescendants(playerEntity, Aspect.all(Unit.class))
                  .forEach(unitEntity -> printUnit(unitEntity, false));
              System.out.println("    Defending");
              coreSystem
                  .getDescendants(playerEntity, Aspect.all(Unit.class))
                  .forEach(unitEntity -> printUnit(unitEntity, true));
            });
  }

  private void printUnit(int unitEntity, boolean withActions) {
    if (withActions) {
      printActions(unitEntity);
    } else {
      System.out.printf("    *%d %s %s\n", unitEntity, name(unitEntity), mUnit.get(unitEntity));
    }
  }
}
