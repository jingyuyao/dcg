package com.dcg.forge;

import com.artemis.annotations.Wire;
import com.dcg.battle.CreateUnit;
import com.dcg.card.CreateCard;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.player.DrawCards;

public class InitializeForgeDeck extends Command {
  @Wire protected CommandChain commandChain;

  @Override
  protected void run() {
    for (int i = 0; i < 50; i++) {
      commandChain.addEnd(new CreateCard("Yeti", 2).addOnEnterEffects(new CreateUnit("Yeti", 2)));
    }
    for (int i = 0; i < 5; i++) {
      commandChain.addEnd(new CreateCard("Wisdom of the Elders", 5).addOnEnterEffects(new DrawCards(2)));
    }
  }
}
