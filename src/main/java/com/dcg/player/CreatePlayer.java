package com.dcg.player;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.dcg.card.Card;
import com.dcg.card.Deck;
import com.dcg.command.Command;

public class CreatePlayer extends Command {
  private final String name;
  World world;
  ComponentMapper<Player> mPlayer;
  ComponentMapper<PlayerOwned> mPlayerOwned;
  ComponentMapper<Card> mCard;
  ComponentMapper<Deck> mDrawPile;

  public CreatePlayer(String name) {
    this.name = name;
  }

  @Override
  public void run() {
    int playerEntity = world.create();
    mPlayer.create(playerEntity).name = name;
    // TODO: make these actions
    for (int i = 0; i < 11; i++) {
      int cardEntity = world.create();
      Card card = mCard.create(cardEntity);
      card.name = "b" + i;
      mDrawPile.create(cardEntity);
      mPlayerOwned.create(cardEntity).playerEntity = playerEntity;
    }
  }

  @Override
  public String toString() {
    return super.toString() + name;
  }
}
