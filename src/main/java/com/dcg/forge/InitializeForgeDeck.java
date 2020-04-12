package com.dcg.forge;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.dcg.card.Card;
import com.dcg.card.Strength;
import com.dcg.command.Command;
import com.dcg.location.Deck;
import java.util.Random;

public class InitializeForgeDeck extends Command {
  private static final Random random = new Random();
  World world;
  ComponentMapper<Card> mCard;
  ComponentMapper<Deck> mDeck;
  ComponentMapper<Strength> mStrength;

  @Override
  public void run() {
    for (int i = 0; i < 50; i++) {
      int cardEntity = world.create();
      mCard.create(cardEntity).name = "F" + i;
      mDeck.create(cardEntity);
      if (random.nextBoolean()) {
        mStrength.create(cardEntity).value = random.nextInt(5) + 1;
      }
    }
  }
}
