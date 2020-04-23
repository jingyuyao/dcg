package com.dcg.forge;

import com.artemis.Aspect;
import com.artemis.annotations.Wire;
import com.dcg.action.CreateAction;
import com.dcg.card.Card;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.game.Owned;
import com.dcg.location.Deck;
import com.dcg.location.ForgeRow;
import com.dcg.location.MoveLocation;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RefillForgeRow extends AbstractCommandBuilder {
  @Wire protected Random random;

  @Override
  protected void run(int originEntity, List<Integer> targets, int value) {
    long forgeRowCount = coreSystem.getStream(Aspect.all(Card.class, ForgeRow.class)).count();
    if (forgeRowCount < 6) {
      List<Integer> forgeDeck =
          coreSystem
              .getStream(Aspect.all(Card.class, Deck.class).exclude(Owned.class))

              .collect(Collectors.toList());
      if (forgeDeck.size() > 0) {
        int cardEntity = forgeDeck.get(random.nextInt(forgeDeck.size()));
        commandChain.addEnd(
            new MoveLocation(ForgeRow.class).build(world, cardEntity),
            new CreateAction(new BuyCard()).build(world, cardEntity),
            build(world, -1));
      } else {
        System.out.println("No more forge cards");
      }
    }
  }
}
