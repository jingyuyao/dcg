package com.dcg.battle;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.player.AdjustHp;
import java.util.List;
import java.util.stream.Collectors;

public class PerformBattle extends AbstractCommandBuilder {;
  protected ComponentMapper<Unit> mUnit;

  @Override
  protected void run() {
    List<Integer> currentPlayerUnits =
        coreSystem
            .getDescendants(sourceEntity, Aspect.all(Unit.class))
            .boxed()
            .collect(Collectors.toList());
    coreSystem
        .getStream(Aspect.all(Unit.class))
        .filter(unitEntity -> !currentPlayerUnits.contains(unitEntity))
        .forEach(this::attack);
  }

  private void attack(int attackingUnitEntity) {
    Unit attackingUnit = mUnit.get(attackingUnitEntity);
    commandChain.addEnd(new AdjustHp(-attackingUnit.strength).build(world, sourceEntity));
    if (attackingUnit.lifeSteal) {
      commandChain.addEnd(new AdjustHp(attackingUnit.strength).build(world, attackingUnitEntity));
    }
    // TODO: use command
    world.delete(attackingUnitEntity);
  }
}
