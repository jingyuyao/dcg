package com.dcg.player;

import com.dcg.command.Command;

public class DrawCard implements Command {

  private final Player player;

  public DrawCard(Player player) {
    this.player = player;
  }

  @Override
  public void run() {
    player.hand.add(player.deck.draw());
    // TODO: compute commands from the card and add them as options for the player
  }

  @Override
  public String toString() {
    return "DrawCard{" +
        "playerName=" + player.name +
        '}';
  }
}
