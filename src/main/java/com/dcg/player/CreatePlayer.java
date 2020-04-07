package com.dcg.player;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.dcg.card.Card;
import com.dcg.command.Command;

public class CreatePlayer implements Command {

  private final String name;
  World world;
  ComponentMapper<Player> mPlayer;
  ComponentMapper<PlayerOwned> mPlayerOwned;
  ComponentMapper<Card> mCard;
  ComponentMapper<DrawPile> mDrawPile;

  public CreatePlayer(String name) {
    this.name = name;
  }

  @Override
  public void run() {
    int playerEntity = world.create();
    mPlayer.create(playerEntity).name = name;
    for (int i = 0; i < 11; i++) {
      int cardEntity = world.create();
      mCard.create(cardEntity).name = "c" + i;
      mPlayerOwned.create(cardEntity).playerEntity = playerEntity;
      mDrawPile.create(cardEntity);
    }
  }

  @Override
  public String toString() {
    return "CreatePlayer{" +
        "name='" + name + '\'' +
        '}';
  }
}
