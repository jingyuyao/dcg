package com.dcg.forge;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.dcg.card.Card;
import com.dcg.card.Strength;
import com.dcg.card.Unit;
import com.dcg.command.Command;
import com.dcg.location.Deck;

public class InitializeForgeDeck extends Command {

  World world;
  ComponentMapper<Card> mCard;
  ComponentMapper<Deck> mDeck;
  ComponentMapper<Unit> mUnit;
  ComponentMapper<Strength> mStrength;

  @Override
  public void run() {
    for (int i = 0; i < 50; i++) {
      boolean isUnit = i % 2 == 0;
      int cardEntity = world.create();
      mCard.create(cardEntity).name = "F" + i;
      mDeck.create(cardEntity);
      if (isUnit) {
        mUnit.create(cardEntity);
        mStrength.create(cardEntity).value = i % 5;
      }
    }
  }
}
