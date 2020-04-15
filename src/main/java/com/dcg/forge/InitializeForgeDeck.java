package com.dcg.forge;

import com.artemis.annotations.Wire;
import com.dcg.battle.CreateUnit;
import com.dcg.card.CreateCard;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;

public class InitializeForgeDeck extends Command {
  @Wire protected CommandChain commandChain;

  @Override
  public void run() {
    for (int i = 0; i < 50; i++) {
      commandChain.addEnd(new CreateCard("Yeti", 2).addEffects(new CreateUnit("Yeti", 2)));
    }
  }
}
