package com.dcg.battle;

import com.artemis.BaseEntitySystem;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.dcg.card.BattleArea;
import com.dcg.card.Card;
import com.dcg.card.MoveLocation;
import com.dcg.card.PlayArea;
import com.dcg.card.Unit;
import com.dcg.command.CommandChain;
import com.dcg.ownership.Owned;

@All({Card.class, Owned.class, PlayArea.class, Unit.class})
public class EnterBattleSystem extends BaseEntitySystem {
  @Wire CommandChain commandChain;

  @Override
  protected void inserted(int entityId) {
    super.inserted(entityId);
    commandChain.addStart(new MoveLocation(entityId, BattleArea.class));
  }

  @Override
  protected void processSystem() {}
}
