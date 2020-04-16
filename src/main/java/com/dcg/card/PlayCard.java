package com.dcg.card;

import com.artemis.annotations.Wire;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.location.MoveLocation;
import com.dcg.location.PlayArea;

public class PlayCard extends Command {
  private final int cardEntity;
  @Wire protected CommandChain commandChain;

  public PlayCard(int cardEntity) {
    this.cardEntity = cardEntity;
  }

  @Override
  protected void run() {
    commandChain.addEnd(new MoveLocation(cardEntity, PlayArea.class).setOwner(owner));
  }
}
