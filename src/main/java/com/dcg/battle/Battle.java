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
import java.util.List;
import java.util.stream.Collectors;

public class Battle extends Command {
  private final int attackingPlayerEntity;
  private final int defendingPlayerEntity;
  @Wire CommandChain commandChain;
  OwnershipSystem ownershipSystem;
  ComponentMapper<Player> mPlayer;
  ComponentMapper<Strength> mStrength;
  ComponentMapper<Blocking> mBlocking;

  public Battle(int attackingPlayerEntity, int defendingPlayerEntity) {
    this.attackingPlayerEntity = attackingPlayerEntity;
    this.defendingPlayerEntity = defendingPlayerEntity;
  }

  @Override
  public void run() {
    List<Integer> attackingEntities =
        ownershipSystem.getOwnedBy(
            attackingPlayerEntity, Aspect.all(BattleArea.class, Strength.class));
    List<Integer> blockingEntities =
        ownershipSystem.getOwnedBy(
            defendingPlayerEntity, Aspect.all(BattleArea.class, Strength.class, Blocking.class));
    List<Integer> blockedEntities =
        blockingEntities.stream()
            .map(e -> mBlocking.get(e).attackingEntity)
            .collect(Collectors.toList());
    Player defendingPlayer = mPlayer.get(defendingPlayerEntity);

    for (int attackingEntity : attackingEntities) {
      if (!blockedEntities.contains(attackingEntity)) {
        int damage = mStrength.get(attackingEntity).value;
        defendingPlayer.hp -= damage;
        System.out.printf("    %s got hit by %d\n", defendingPlayer.name, damage);
      } else {
        System.out.printf("    *%d was blocked\n", attackingEntity);
      }
      commandChain.addStart(new MoveLocation(attackingEntity, DiscardPile.class));
    }

    for (int blockingEntity : blockedEntities) {
      mBlocking.remove(blockingEntity);
      commandChain.addStart(new MoveLocation(blockingEntity, DiscardPile.class));
    }
  }
}
