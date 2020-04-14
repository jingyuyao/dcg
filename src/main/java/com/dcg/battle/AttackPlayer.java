package com.dcg.battle;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.World;
import com.dcg.command.Command;
import com.dcg.ownership.OwnershipSystem;
import com.dcg.player.Player;
import java.util.List;

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
  public void run() {
    List<Integer> attackingEntities =
        ownershipSystem.getOwnedBy(attackingPlayerEntity, Aspect.all(Unit.class));

    Player defendingPlayer = mPlayer.get(defendingPlayerEntity);
    for (int attackingEntity : attackingEntities) {
      int damage = mUnit.get(attackingEntity).strength;
      defendingPlayer.hp -= damage;
      System.out.printf("    %s got hit by %d\n", defendingPlayer.name, damage);
      world.delete(attackingEntity);
    }
  }
}
