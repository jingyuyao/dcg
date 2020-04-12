package com.dcg.player;

import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.dcg.card.Card;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.location.MoveLocation;
import com.dcg.location.PlayArea;

public class PlayCard extends Command {
  private final int cardEntity;
  @Wire protected CommandChain commandChain;
  protected ComponentMapper<Card> mCard;

  public PlayCard(int cardEntity) {
    this.cardEntity = cardEntity;
  }

  @Override
  public void run() {
    commandChain.addStart(new MoveLocation(cardEntity, PlayArea.class));
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), mCard.get(cardEntity));
  }
}
