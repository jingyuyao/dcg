package com.dcg.player;

import com.dcg.command.Command;
import com.dcg.deck.Card;

public class DiscardHand implements Command {

  private final Player player;

  public DiscardHand(Player player) {
    this.player = player;
  }

  @Override
  public void run() {
    for (Card card : player.hand) {
      player.deck.discard(card);
    }
    player.hand.clear();
  }

  @Override
  public String toString() {
    return "DiscardHand{" + "name=" + player.name + '}';
  }
}
