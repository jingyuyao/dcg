package com.dcg.player;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.dcg.card.Card;
import com.dcg.card.Strength;
import com.dcg.command.Command;
import com.dcg.location.Deck;
import com.dcg.ownership.Owned;

public class CreatePlayer extends Command {
  private final String name;
  World world;
  ComponentMapper<Player> mPlayer;
  ComponentMapper<Owned> mOwned;
  ComponentMapper<Card> mCard;
  ComponentMapper<Deck> mDeck;
  ComponentMapper<Strength> mStrength;

  public CreatePlayer(String name) {
    this.name = name;
  }

  @Override
  public void run() {
    int playerEntity = world.create();
    mPlayer.create(playerEntity).name = name;
    for (int i = 0; i < 7; i++) {
      boolean hasStrength = i % 2 == 0;
      int cardEntity = world.create();
      mCard.create(cardEntity).name = "P" + i;
      mDeck.create(cardEntity);
      mOwned.create(cardEntity).owner = playerEntity;
      if (hasStrength) {
        mStrength.create(cardEntity).value = i % 5;
      }
    }
  }

  @Override
  public String toString() {
    return super.toString() + name;
  }
}
