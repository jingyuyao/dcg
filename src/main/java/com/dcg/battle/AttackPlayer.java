package com.dcg.battle;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.World;
import com.dcg.command.Command;
import com.dcg.ownership.OwnershipSystem;
import com.dcg.player.Player;

public class AttackPlayer extends Command {
  private final int attackingPlayerEntity;
  private final int defendingPlayerEntity;
  protected World world;
  protected OwnershipSystem ownershipSystem;
  protected ComponentMapper<Player> mPlayer;
  protected ComponentMapper<Unit> mUnit;

  public AttackPlayer(int attackingPlayerEntity, int defendingPlayerEntity) {
    this.attackingPlayerEntity = attackingPlayerEntity;
    this.defendingPlayerEntity = defendingPlayerEntity;
  }

  @Override
  protected void run() {
    Player defendingPlayer = mPlayer.get(defendingPlayerEntity);
    ownershipSystem
        .getDescendants(attackingPlayerEntity, Aspect.all(Unit.class))
        .forEach(unitEntity -> attack(unitEntity, defendingPlayer));
  }

  private void attack(int unitEntity, Player player) {
    int damage = mUnit.get(unitEntity).strength;
    player.hp -= damage;
    System.out.printf("    %s got hit by %d\n", player.name, damage);
    world.delete(unitEntity);
  }
}
