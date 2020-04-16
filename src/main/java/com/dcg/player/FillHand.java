package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.annotations.Wire;
import com.dcg.card.Card;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.location.Hand;
import com.dcg.ownership.OwnershipSystem;

public class FillHand extends Command {
  private final int num;
  @Wire protected CommandChain commandChain;
  protected OwnershipSystem ownershipSystem;

  public FillHand(int num) {
    this.num = num;
  }

  @Override
  protected void run() {
    long cardsInHandCount =
        ownershipSystem.getOwnedBy(owner, Aspect.all(Card.class, Hand.class)).count();
    for (int i = 0; i < num - cardsInHandCount; i++) {
      commandChain.addEnd(new DrawCard(owner));
    }
  }
}
