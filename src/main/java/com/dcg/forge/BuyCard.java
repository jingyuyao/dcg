package com.dcg.forge;

import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.dcg.card.Card;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.location.Deck;
import com.dcg.location.MoveLocation;
import com.dcg.ownership.Own;
import com.dcg.turn.AdjustPower;
import com.dcg.turn.Turn;
import com.dcg.turn.TurnSystem;

public class BuyCard extends Command {
  private final int cardEntity;
  @Wire protected CommandChain commandChain;
  protected TurnSystem turnSystem;
  protected ComponentMapper<Card> mCard;

  public BuyCard(int cardEntity) {
    this.cardEntity = cardEntity;
  }

  @Override
  protected boolean isInputValid() {
    Turn turn = turnSystem.getTurn();
    Card card = mCard.get(cardEntity);
    if (turn.powerPool < card.cost) {
      System.out.printf("    Not enough power to buy *%s\n", card);
      return false;
    }
    return true;
  }

  @Override
  protected void run() {
    Card card = mCard.get(cardEntity);
    commandChain.addEnd(
        new AdjustPower(-card.cost).setOwner(owner),
        new Own(turnSystem.getPlayerEntity(), cardEntity).setOwner(owner),
        new MoveLocation(cardEntity, Deck.class).setOwner(owner),
        new DrawFromForge().setOwner(owner));
  }
}
