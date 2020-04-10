package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.annotations.Wire;
import com.artemis.utils.IntBag;
import com.dcg.card.Card;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.location.BattleArea;
import com.dcg.location.DiscardPile;
import com.dcg.location.MoveLocation;
import com.dcg.ownership.Owned;

public class DiscardBattleArea extends Command {
  @Wire CommandChain commandChain;
  AspectSubscriptionManager manager;

  @Override
  public void run() {
    IntBag playArea =
        manager.get(Aspect.all(Card.class, Owned.class, BattleArea.class)).getEntities();
    for (int i = 0; i < playArea.size(); i++) {
      commandChain.addStart(new MoveLocation(playArea.get(i), DiscardPile.class));
    }
  }
}
