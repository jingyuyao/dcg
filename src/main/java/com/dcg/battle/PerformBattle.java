package com.dcg.battle;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.command.CommandBase;
import com.dcg.game.AspectSystem;
import com.dcg.game.OwnershipSystem;
import com.dcg.player.Player;
import java.util.List;
import java.util.stream.Collectors;

public class PerformBattle extends CommandBase {
  protected AspectSystem aspectSystem;
  protected OwnershipSystem ownershipSystem;
  protected ComponentMapper<Player> mPlayer;
  protected ComponentMapper<Unit> mUnit;

  @Override
  protected void run() {
    // TODO: dedupe logic with print unit
    List<Integer> currentPlayerUnits =
        ownershipSystem
            .getDescendants(sourceEntity, Aspect.all(Unit.class))
            .boxed()
            .collect(Collectors.toList());
    Player defendingPlayer = mPlayer.get(sourceEntity);
    aspectSystem
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
