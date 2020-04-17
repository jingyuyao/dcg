package com.dcg.forge;

import com.artemis.Aspect;
import com.artemis.annotations.Wire;
import com.dcg.action.CreateAction;
import com.dcg.card.Card;
import com.dcg.command.CommandBase;
import com.dcg.command.CommandChain;
import com.dcg.game.CoreSystem;
import com.dcg.game.Owned;
import com.dcg.location.Deck;
import com.dcg.location.ForgeRow;
import com.dcg.location.MoveLocation;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DrawFromForge extends CommandBase {
  @Wire protected Random random;
  @Wire protected CommandChain commandChain;
  protected CoreSystem coreSystem;

  @Override
  protected void run() {
    List<Integer> forgeDeck =
        coreSystem
            .getStream(Aspect.all(Card.class, Deck.class).exclude(Owned.class))
            .boxed()
            .collect(Collectors.toList());
    int cardEntity = forgeDeck.get(random.nextInt(forgeDeck.size()));
    commandChain.addEnd(
        new MoveLocation(ForgeRow.class).build(world, cardEntity),
        new CreateAction(new BuyCard()).build(world, cardEntity));
  }
}
