package com.dcg.player;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.dcg.card.Card;
import com.dcg.card.Card.Location;
import com.dcg.command.Command;

public class CreatePlayer implements Command {
  private final String name;
  World world;
  ComponentMapper<Player> mPlayer;
  ComponentMapper<PlayerOwned> mPlayerOwned;
  ComponentMapper<Card> mCard;

  public CreatePlayer(String name) {
    this.name = name;
  }

  @Override
  public void run() {
    int playerEntity = world.create();
    mPlayer.create(playerEntity).name = name;
    for (int i = 0; i < 11; i++) {
      int cardEntity = world.create();
      Card card = mCard.create(cardEntity);
      card.name = "c" + i;
      card.location = Location.DRAW_PILE;
      mPlayerOwned.create(cardEntity).playerEntity = playerEntity;
    }
  }

  @Override
  public String toString() {
    return "CreatePlayer " + name;
  }
}
