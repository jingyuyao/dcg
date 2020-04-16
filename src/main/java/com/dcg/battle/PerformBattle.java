package com.dcg.battle;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.World;
import com.dcg.command.Command;
import com.dcg.ownership.OwnershipSystem;
import com.dcg.player.Player;
import com.dcg.turn.Turn;
import com.dcg.turn.TurnSystem;

public class PerformBattle extends Command {
  protected World world;
  protected OwnershipSystem ownershipSystem;
  protected TurnSystem turnSystem;
  protected ComponentMapper<Player> mPlayer;
  protected ComponentMapper<Unit> mUnit;

  @Override
  protected void run() {
    Turn turn = turnSystem.getTurn();
    if (turn.previousPlayerEntity != -1) {
      Player defendingPlayer = mPlayer.get(owner);
      ownershipSystem
          .getDescendants(turn.previousPlayerEntity, Aspect.all(Unit.class))
          .forEach(unitEntity -> attack(unitEntity, defendingPlayer));
    }
  }

  private void attack(int unitEntity, Player player) {
    int damage = mUnit.get(unitEntity).strength;
    player.hp -= damage;
    System.out.printf("    %s got hit by %d\n", player.name, damage);
    world.delete(unitEntity);
  }
}
