package com.dcg.player;

import com.dcg.command.Command;

public class DrawCards implements Command {

  private final Player player;
  private final int num;

  public DrawCards(Player player, int num) {
    this.player = player;
    this.num = num;
  }

  @Override
  public void run() {
    // TODO: compute commands from the card and add them as options for the player
    for (int i = 0; i < num; i++) {
      player.hand.add(player.deck.draw());
    }
  }

  @Override
  public String toString() {
    return "DrawCards{" + "name=" + player.name + ", num=" + num + '}';
  }
}
