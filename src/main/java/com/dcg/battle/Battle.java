package com.dcg.battle;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.dcg.card.Strength;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.location.BattleArea;
import com.dcg.location.DiscardPile;
import com.dcg.location.MoveLocation;
import com.dcg.ownership.OwnershipSystem;
import com.dcg.player.Player;
import com.dcg.util.AspectSystem;
import java.util.List;

public class Battle extends Command {
  private final int attackingPlayerEntity;
  private final int defendingPlayerEntity;
  @Wire CommandChain commandChain;
  AspectSystem aspectSystem;
  OwnershipSystem ownershipSystem;
  ComponentMapper<Player> mPlayer;
  ComponentMapper<Strength> mStrength;
  ComponentMapper<Attacking> mAttacking;
  ComponentMapper<Blocking> mBlocking;
  ComponentMapper<Blocked> mBlocked;

  public Battle(int attackingPlayerEntity, int defendingPlayerEntity) {
    this.attackingPlayerEntity = attackingPlayerEntity;
    this.defendingPlayerEntity = defendingPlayerEntity;
  }

  @Override
  public void run() {
    List<Integer> attackingEntities =
        ownershipSystem.getOwnedBy(
            attackingPlayerEntity, Aspect.all(BattleArea.class, Strength.class, Attacking.class));

    Player defendingPlayer = mPlayer.get(defendingPlayerEntity);
    for (int attackingEntity : attackingEntities) {
      int damage = mStrength.get(attackingEntity).value;
      defendingPlayer.hp -= damage;
      System.out.printf("    %s got hit by %d\n", defendingPlayer.name, damage);
      mAttacking.remove(attackingEntity);
      commandChain.addStart(new MoveLocation(attackingEntity, DiscardPile.class));
    }

    for (int entity :
        aspectSystem.get(Aspect.all(BattleArea.class).one(Blocking.class, Blocked.class))) {
      mBlocking.remove(entity);
      mBlocked.remove(entity);
      commandChain.addStart(new MoveLocation(entity, DiscardPile.class));
    }
  }
}
