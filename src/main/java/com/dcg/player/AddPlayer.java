package com.dcg.player;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.dcg.command.Command;
import com.dcg.deck.Card;

public class AddPlayer implements Command {
  private final String name;
  World world;
  ComponentMapper<Player> mPlayer;

  public AddPlayer(String name) {
    this.name = name;
  }

  @Override
  public void run() {
    int entity = world.create();
    Player player = mPlayer.create(entity);
    player.name = name;
    // TODO: replace with a command to add card to deck, perhaps a new player system
    // and override inserted?
    for (int i = 0; i < 7; i++) {
      player.deck.add(new Card("P" + i));
    }
  }

  @Override
  public String toString() {
    return "AddPlayer{" + "name='" + name + '\'' + '}';
  }
}
