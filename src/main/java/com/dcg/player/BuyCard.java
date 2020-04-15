package com.dcg.player;

import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.dcg.card.Card;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.location.Deck;
import com.dcg.location.MoveLocation;
import com.dcg.ownership.Own;
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
  public boolean canRun() {
    Turn turn = turnSystem.getCurrentTurn();
    Card card = mCard.get(cardEntity);
    if (turn.powerPool < card.cost) {
      System.out.printf("    Not enough power to buy *%s\n", card);
      return false;
    }
    return true;
  }

  @Override
  public void run() {
    Card card = mCard.get(cardEntity);
    commandChain.addStart(
        new AdjustPower(-card.cost),
        new Own(turnSystem.getCurrentPlayerEntity(), cardEntity),
        new MoveLocation(cardEntity, Deck.class));
  }

  @Override
  public String toString() {
    return String.format("%s *%d", super.toString(), cardEntity);
  }
}
