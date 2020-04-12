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

public class Battle extends Command {
  private final int attackingPlayerEntity;
  private final int defendingPlayerEntity;
  @Wire CommandChain commandChain;
  OwnershipSystem ownershipSystem;
  ComponentMapper<Player> mPlayer;
  ComponentMapper<Strength> mStrength;

  public Battle(int attackingPlayerEntity, int defendingPlayerEntity) {
    this.attackingPlayerEntity = attackingPlayerEntity;
    this.defendingPlayerEntity = defendingPlayerEntity;
  }

  @Override
  public void run() {
    List<Integer> attackingEntities =
        ownershipSystem.getOwnedBy(
            attackingPlayerEntity, Aspect.all(BattleArea.class, Strength.class));

    Player defendingPlayer = mPlayer.get(defendingPlayerEntity);
    for (int attackingEntity : attackingEntities) {
      int damage = mStrength.get(attackingEntity).value;
      defendingPlayer.hp -= damage;
      System.out.printf("    %s got hit by %d\n", defendingPlayer.name, damage);
      commandChain.addStart(new MoveLocation(attackingEntity, DiscardPile.class));
    }
  }
}
