package com.dcg.forge;

import com.artemis.Aspect;
import com.artemis.annotations.Wire;
import com.dcg.card.Card;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.game.AspectSystem;
import com.dcg.location.Deck;
import com.dcg.location.ForgeRow;
import com.dcg.location.MoveLocation;
import com.dcg.ownership.Owned;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DrawFromForge extends Command {
  @Wire protected Random random;
  @Wire protected CommandChain commandChain;
  protected AspectSystem aspectSystem;

  @Override
  protected void run() {
    List<Integer> forgeDeck =
        aspectSystem
            .getStream(Aspect.all(Card.class, Deck.class).exclude(Owned.class))
            .boxed()
            .collect(Collectors.toList());
    commandChain.addEnd(
        new MoveLocation(forgeDeck.get(random.nextInt(forgeDeck.size())), ForgeRow.class));
  }
}
