package com.dcg.battle;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.utils.IntBag;
import com.dcg.card.Card;
import com.dcg.card.Strength;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.location.BattleArea;
import com.dcg.player.Damage;

public class Battle extends Command {
  private final int attackingPlayerEntity;
  private final int defendingPlayerEntity;
  @Wire CommandChain commandChain;
  AspectSubscriptionManager manager;
  ComponentMapper<Strength> mStrength;

  public Battle(int attackingPlayerEntity, int defendingPlayerEntity) {
    this.attackingPlayerEntity = attackingPlayerEntity;
    this.defendingPlayerEntity = defendingPlayerEntity;
  }

  @Override
  public void run() {
    // TODO: defense
    IntBag battleArea = manager.get(Aspect.all(Card.class, BattleArea.class)).getEntities();
    for (int i = 0; i < battleArea.size(); i++) {
      int cardEntity = battleArea.get(i);
      if (mStrength.has(cardEntity)) {
        commandChain.addStart(new Damage(defendingPlayerEntity, mStrength.get(cardEntity).value));
      }
    }
  }
}
