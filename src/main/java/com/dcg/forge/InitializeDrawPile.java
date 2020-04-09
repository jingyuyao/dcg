package com.dcg.forge;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.dcg.card.Card;
import com.dcg.card.DrawPile;
import com.dcg.command.Command;

public class InitializeDrawPile implements Command {

  World world;
  ComponentMapper<Card> mCard;
  ComponentMapper<DrawPile> mDrawPile;

  @Override
  public void run() {
    for (int i = 0; i < 50; i++) {
      int cardEntity = world.create();
      Card card = mCard.create(cardEntity);
      card.name = "f" + i;
      mDrawPile.create(cardEntity);
    }
  }
}
