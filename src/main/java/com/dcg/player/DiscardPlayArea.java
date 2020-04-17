package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.annotations.Wire;
import com.dcg.card.Card;
import com.dcg.command.CommandBase;
import com.dcg.command.CommandChain;
import com.dcg.game.CoreSystem;
import com.dcg.location.DiscardPile;
import com.dcg.location.MoveLocation;
import com.dcg.location.PlayArea;

public class DiscardPlayArea extends CommandBase {
  @Wire protected CommandChain commandChain;
  protected CoreSystem coreSystem;

  @Override
  protected void run() {
    coreSystem
        .getOwnedBy(sourceEntity, Aspect.all(Card.class, PlayArea.class))
        .forEach(
            cardEntity ->
                commandChain.addEnd(new MoveLocation(DiscardPile.class).build(world, cardEntity)));
  }
}
