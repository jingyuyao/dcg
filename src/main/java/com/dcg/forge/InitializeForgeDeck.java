package com.dcg.forge;

import com.artemis.annotations.Wire;
import com.dcg.card.CreateCard;
import com.dcg.card.Unit;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.location.Deck;

public class InitializeForgeDeck extends Command {

  @Wire CommandChain commandChain;

  @Override
  public void run() {
    for (int i = 0; i < 50; i++) {
      commandChain.addStart(new CreateCard("f" + i, Deck.class).addTag(Unit.class));
    }
  }
}
