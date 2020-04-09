package com.dcg.player;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.annotations.Wire;
import com.dcg.card.CreateCard;
import com.dcg.card.Deck;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;

public class CreatePlayer extends Command {
  private final String name;
  @Wire CommandChain commandChain;
  World world;
  ComponentMapper<Player> mPlayer;

  public CreatePlayer(String name) {
    this.name = name;
  }

  @Override
  public void run() {
    int playerEntity = world.create();
    mPlayer.create(playerEntity).name = name;
    // TODO: pass in the data for the basic card
    for (int i = 0; i < 11; i++) {
      commandChain.addStart(new CreateCard("b" + i, Deck.class, playerEntity));
    }
  }

  @Override
  public String toString() {
    return super.toString() + name;
  }
}
