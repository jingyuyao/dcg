package com.dcg.battle;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.player.Player;
import java.util.List;
import java.util.stream.Collectors;

public class PerformBattle extends AbstractCommandBuilder {;
  protected ComponentMapper<Player> mPlayer;
  protected ComponentMapper<Unit> mUnit;

  @Override
  protected void run() {
    List<Integer> currentPlayerUnits =
        coreSystem
            .getDescendants(sourceEntity, Aspect.all(Unit.class))
            .boxed()
            .collect(Collectors.toList());
    Player defendingPlayer = mPlayer.get(sourceEntity);
    coreSystem
        .getStream(Aspect.all(Unit.class))
        .filter(unitEntity -> !currentPlayerUnits.contains(unitEntity))
        .forEach(unitEntity -> attack(unitEntity, defendingPlayer));
  }

  private void attack(int unitEntity, Player player) {
    int damage = mUnit.get(unitEntity).strength;
    player.hp -= damage;
    System.out.printf("    %s got hit by %d\n", player.name, damage);
    world.delete(unitEntity);
  }
}
