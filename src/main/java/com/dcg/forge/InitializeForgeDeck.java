package com.dcg.forge;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.annotations.Wire;
import com.dcg.battle.Strength;
import com.dcg.card.Card;
import com.dcg.command.Command;
import com.dcg.location.Deck;
import java.util.Random;

public class InitializeForgeDeck extends Command {
  @Wire protected Random random;
  protected World world;
  protected ComponentMapper<Card> mCard;
  protected ComponentMapper<Deck> mDeck;
  protected ComponentMapper<Strength> mStrength;

  @Override
  public void run() {
    for (int i = 0; i < 50; i++) {
      int cardEntity = world.create();
      int cost = random.nextInt(5) + 1;
      Card card = mCard.create(cardEntity);
      card.name = "F";
      card.cost = cost;
      mStrength.create(cardEntity).value = cost;
      mDeck.create(cardEntity);
    }
  }
}
