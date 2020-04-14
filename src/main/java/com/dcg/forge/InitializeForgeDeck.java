package com.dcg.forge;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.annotations.Wire;
import com.dcg.card.Card;
import com.dcg.command.Command;
import com.dcg.effect.CreateUnit;
import com.dcg.effect.OnPlay;
import com.dcg.location.Deck;
import java.util.Random;

public class InitializeForgeDeck extends Command {
  @Wire protected Random random;
  protected World world;
  protected ComponentMapper<Card> mCard;
  protected ComponentMapper<Deck> mDeck;
  protected ComponentMapper<OnPlay> mOnPlay;

  @Override
  public void run() {
    for (int i = 0; i < 50; i++) {
      int cardEntity = world.create();
      Card card = mCard.create(cardEntity);
      card.name = "F" + i;
      card.cost = random.nextInt(5) + 1;
      OnPlay onPlay = mOnPlay.create(cardEntity);
      onPlay.effects.add(new CreateUnit(card.name, card.cost));
      mDeck.create(cardEntity);
    }
  }
}
