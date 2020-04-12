package com.dcg.battle;

import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.dcg.card.Card;
import com.dcg.card.Strength;
import com.dcg.command.CommandChain;
import com.dcg.location.BattleArea;
import com.dcg.location.MoveLocation;
import com.dcg.location.PlayArea;

@All({Card.class, PlayArea.class, Strength.class})
public class EnterBattleSystem extends BaseEntitySystem {
  @Wire CommandChain commandChain;
  ComponentMapper<Attacking> mAttacking;

  @Override
  protected void inserted(int entityId) {
    super.inserted(entityId);
    mAttacking.create(entityId);
    commandChain.addStart(new MoveLocation(entityId, BattleArea.class));
  }

  @Override
  protected void processSystem() {}
}
