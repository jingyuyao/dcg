package com.dcg.player;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.annotations.Wire;
import com.dcg.card.CreateCard;
import com.dcg.card.Deck;
import com.dcg.card.Spell;
import com.dcg.card.Unit;
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
    for (int i = 0; i < 6; i++) {
      commandChain.addStart(
          new CreateCard("s" + i, Deck.class).setOwner(playerEntity).addTag(Spell.class));
    }
    for (int i = 0; i < 5; i++) {
      commandChain.addStart(
          new CreateCard("u" + i, Deck.class).setOwner(playerEntity).addTag(Unit.class));
    }
  }

  @Override
  public String toString() {
    return super.toString() + name;
  }
}
