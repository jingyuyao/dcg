package com.dcg.player;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.dcg.command.Command;

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
    mPlayer.create(entity).name = name;
  }

  @Override
  public String toString() {
    return "AddPlayer{" + "name='" + name + '\'' + '}';
  }
}
