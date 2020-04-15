package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.annotations.Wire;
import com.dcg.action.CreateAction;
import com.dcg.card.Card;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.location.Hand;
import com.dcg.location.MoveLocation;
import com.dcg.location.PlayArea;
import com.dcg.ownership.OwnershipSystem;
import com.dcg.turn.AdvanceTurn;
import com.dcg.turn.TurnSystem;

public class PlayCard extends Command {
  private final int cardEntity;
  @Wire protected CommandChain commandChain;
  protected OwnershipSystem ownershipSystem;
  protected TurnSystem turnSystem;

  public PlayCard(int cardEntity) {
    this.cardEntity = cardEntity;
  }

  @Override
  public void run() {
    commandChain.addStart(new MoveLocation(cardEntity, PlayArea.class));
    if (ownershipSystem.getPeersOf(cardEntity, Aspect.all(Card.class, Hand.class)).count() == 1) {
      commandChain.addEnd(new CreateAction(turnSystem.getCurrentPlayerEntity(), new AdvanceTurn()));
    }
  }

  @Override
  public String toString() {
    return String.format("%s *%d", super.toString(), cardEntity);
  }
}
