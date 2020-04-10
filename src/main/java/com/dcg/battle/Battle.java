package com.dcg.battle;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.utils.IntBag;
import com.dcg.card.Card;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.location.BattleArea;
import com.dcg.player.DiscardBattleArea;

public class Battle extends Command {
  @Wire CommandChain commandChain;
  AspectSubscriptionManager manager;
  ComponentMapper<Card> mCard;

  @Override
  public void run() {
    IntBag battleArea = manager.get(Aspect.all(Card.class, BattleArea.class)).getEntities();
    if (battleArea.size() > 0) {
      System.out.print("    ");
      for (int i = 0; i < battleArea.size(); i++) {
        System.out.print(mCard.get(battleArea.get(i)) + " x ");
      }
      System.out.println();
      commandChain.addStart(new DiscardBattleArea());
    }
  }
}
