package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.dcg.card.Card;
import com.dcg.command.CommandChain;
import com.dcg.game.CoreSystem;
import com.dcg.location.Hand;
import com.dcg.location.MoveLocation;
import com.dcg.location.PlayArea;

@All({Player.class, Turn.class})
public class PlayHandSystem extends IteratingSystem {
  @Wire protected CommandChain commandChain;
  protected CoreSystem coreSystem;

  @Override
  protected void process(int playerEntity) {
    coreSystem
        .getOwnedBy(playerEntity, Aspect.all(Card.class, Hand.class))
        .forEach(
            cardEntity ->
                commandChain.addEnd(new MoveLocation(PlayArea.class).build(world, cardEntity)));
  }
}
