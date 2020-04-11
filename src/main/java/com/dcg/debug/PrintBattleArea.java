package com.dcg.debug;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.ComponentMapper;
import com.artemis.utils.IntBag;
import com.dcg.card.Card;
import com.dcg.card.Strength;
import com.dcg.command.Command;
import com.dcg.location.BattleArea;
import com.dcg.ownership.Owned;
import com.dcg.turn.TurnSystem;

public class PrintBattleArea extends Command {
  TurnSystem turnSystem;
  AspectSubscriptionManager manager;
  ComponentMapper<Card> mCard;
  ComponentMapper<Owned> mOwned;
  ComponentMapper<Strength> mStrength;

  @Override
  public void run() {
    IntBag battleArea =
        manager
            .get(Aspect.all(BattleArea.class, Card.class, Owned.class, Strength.class))
            .getEntities();
    for (int i = 0; i < battleArea.size(); i++) {
      int entity = battleArea.get(i);
      Owned owned = mOwned.get(entity);
      Card card = mCard.get(entity);
      Strength strength = mStrength.get(entity);
      boolean isDefender = turnSystem.getCurrentPlayerEntity() == owned.owner;
      System.out.printf(
          "    *%d %s %s: %s str: %s",
          entity, card, isDefender ? "defender" : "attacker", owned, strength);
      System.out.println();
    }
  }
}
