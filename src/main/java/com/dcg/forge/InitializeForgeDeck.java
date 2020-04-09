package com.dcg.forge;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.dcg.card.Card;
import com.dcg.card.Deck;
import com.dcg.command.Command;

public class InitializeForgeDeck implements Command {

  World world;
  ComponentMapper<Card> mCard;
  ComponentMapper<Deck> mDrawPile;

  @Override
  public void run() {
    for (int i = 0; i < 50; i++) {
      int cardEntity = world.create();
      Card card = mCard.create(cardEntity);
      card.name = "f" + i;
      mDrawPile.create(cardEntity);
    }
  }

  @Override
  public String toString() {
    return "InitializeForgeDeck";
  }
}
