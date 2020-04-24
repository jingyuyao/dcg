package com.dcg.forge;

import com.artemis.Aspect;
import com.artemis.annotations.Wire;
import com.dcg.action.CreateAction;
import com.dcg.card.Card;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandArgs;
import com.dcg.location.ForgeDeck;
import com.dcg.location.ForgeRow;
import com.dcg.location.MoveLocation;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DrawForgeDeck extends AbstractCommandBuilder {
  private final int num;
  @Wire protected Random random;

  public DrawForgeDeck(int num) {
    this.num = num;
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    List<Integer> forgeDeck =
        coreSystem.getStream(Aspect.all(Card.class, ForgeDeck.class)).collect(Collectors.toList());
    for (int i = 0; i < num; i++) {
      if (!forgeDeck.isEmpty()) {
        int cardIndex = random.nextInt(forgeDeck.size());
        int cardEntity = forgeDeck.get(cardIndex);
        forgeDeck.remove(cardIndex);
        commandChain.addEnd(
            new MoveLocation(ForgeRow.class).build(world, cardEntity),
            new CreateAction(new BuyCard().chain(new DrawForgeDeck(1))).build(world, cardEntity));
      } else {
        System.out.println("No more forge cards");
        break;
      }
    }
  }
}
