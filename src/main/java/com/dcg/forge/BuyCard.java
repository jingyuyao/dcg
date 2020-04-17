package com.dcg.forge;

import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.dcg.card.Card;
import com.dcg.command.CommandBase;
import com.dcg.command.CommandChain;
import com.dcg.location.Deck;
import com.dcg.location.MoveLocation;
import com.dcg.ownership.Own;
import com.dcg.turn.AdjustPower;
import com.dcg.turn.Turn;
import com.dcg.turn.TurnSystem;

public class BuyCard extends CommandBase {
  @Wire protected CommandChain commandChain;
  protected TurnSystem turnSystem;
  protected ComponentMapper<Card> mCard;

  @Override
  protected boolean isInputValid() {
    Turn turn = turnSystem.getTurn();
    Card card = mCard.get(owner);
    if (turn.powerPool < card.cost) {
      System.out.printf("    Not enough power to buy *%s\n", card);
      return false;
    }
    return true;
  }

  @Override
  protected void run() {
    Card card = mCard.get(owner);
    commandChain.addEnd(
        new AdjustPower(-card.cost).build(world, owner),
        new Own(owner).build(world, turnSystem.getPlayerEntity()),
        new MoveLocation(Deck.class).build(world, owner),
        new DrawFromForge().build(world, -1));
  }
}
