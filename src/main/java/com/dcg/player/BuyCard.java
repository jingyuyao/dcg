package com.dcg.player;

import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.dcg.card.Card;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.location.Deck;
import com.dcg.location.MoveLocation;
import com.dcg.ownership.Own;
import java.util.List;

public class BuyCard extends Command {
  private final int playerEntity;
  private final int availablePower;
  @Wire protected CommandChain commandChain;
  protected ComponentMapper<Card> mCard;

  public BuyCard(int playerEntity, int availablePower) {
    this.playerEntity = playerEntity;
    this.availablePower = availablePower;
  }

  @Override
  public void run() {
    List<Integer> targets = getTargetEntities();
    if (targets.size() != 1) {
      System.out.println("    Invalid targets for BuyCard");
      return;
    }

    int cardEntity = targets.get(0);
    if (!mCard.has(cardEntity)) {
      System.out.println("    Invalid target for BuyCard");
      return;
    }

    Card card = mCard.get(cardEntity);
    if (availablePower < card.cost) {
      System.out.printf("    Not enough power to buy *%s\n", card);
      return;
    }

    commandChain.addStart(
        new AdjustPower(-card.cost),
        new Own(playerEntity, cardEntity),
        new MoveLocation(cardEntity, Deck.class));
  }

  @Override
  public String toString() {
    return String.format("%s(availablePower:%d)", super.toString(), availablePower);
  }
}
