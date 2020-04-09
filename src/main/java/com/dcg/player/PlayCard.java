package com.dcg.player;

import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.dcg.card.Card;
import com.dcg.card.MoveLocation;
import com.dcg.card.PlayArea;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;

public class PlayCard extends Command {

  private final int cardEntity;
  @Wire CommandChain commandChain;
  ComponentMapper<Card> mCard;

  public PlayCard(int cardEntity) {
    this.cardEntity = cardEntity;
  }

  @Override
  public void run() {
    commandChain.addStart(new MoveLocation(cardEntity, PlayArea.class));
  }

  @Override
  public String toString() {
    return super.toString() + mCard.get(cardEntity);
  }
}
