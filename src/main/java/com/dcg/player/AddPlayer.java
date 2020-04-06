package com.dcg.player;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.dcg.command.Command;
import com.dcg.deck.Card;
import java.util.List;

public class AddPlayer implements Command {

  private final String name;
  private final List<Card> initCards;
  World world;
  ComponentMapper<Player> mPlayer;

  public AddPlayer(String name, List<Card> initCards) {
    this.name = name;
    this.initCards = initCards;
  }

  @Override
  public void run() {
    int entity = world.create();
    Player player = mPlayer.create(entity);
    player.name = name;
    for (Card card : initCards) {
      player.deck.add(card);
    }
  }

  @Override
  public String toString() {
    return "AddPlayer{" + "name='" + name + '\'' + '}';
  }
}
