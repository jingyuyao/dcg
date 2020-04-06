package com.dcg.player;

import com.dcg.command.Command;
import com.dcg.deck.Card;

public class DiscardCard implements Command {

  private final Player player;
  private final Card card;

  public DiscardCard(Player player, Card card) {
    this.player = player;
    this.card = card;
  }

  @Override
  public void run() {
    player.deck.discard(card);
  }
}
