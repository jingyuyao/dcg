package com.dcg.debug;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.card.Card;
import com.dcg.card.Strength;
import com.dcg.command.Command;
import com.dcg.location.BattleArea;
import com.dcg.ownership.Owned;
import com.dcg.turn.TurnSystem;
import com.dcg.util.AspectSystem;

public class PrintBattleArea extends Command {
  TurnSystem turnSystem;
  AspectSystem aspectSystem;
  ComponentMapper<Card> mCard;
  ComponentMapper<Owned> mOwned;
  ComponentMapper<Strength> mStrength;

  @Override
  public void run() {
    for (int entity :
        aspectSystem.get(Aspect.all(BattleArea.class, Card.class, Owned.class, Strength.class))) {
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
