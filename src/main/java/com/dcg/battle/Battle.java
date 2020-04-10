package com.dcg.battle;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.dcg.card.Strength;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.ownership.OwnershipSystem;
import com.dcg.player.Damage;

public class Battle extends Command {
  private final int attackingPlayerEntity;
  private final int defendingPlayerEntity;
  @Wire CommandChain commandChain;
  OwnershipSystem ownershipSystem;
  ComponentMapper<Strength> mStrength;

  public Battle(int attackingPlayerEntity, int defendingPlayerEntity) {
    this.attackingPlayerEntity = attackingPlayerEntity;
    this.defendingPlayerEntity = defendingPlayerEntity;
  }

  @Override
  public void run() {
    // TODO: defense
    for (int entityId :
        ownershipSystem.getOwnedBy(Aspect.all(Strength.class), attackingPlayerEntity)) {
      commandChain.addStart(new Damage(defendingPlayerEntity, mStrength.get(entityId).value));
    }
  }
}
