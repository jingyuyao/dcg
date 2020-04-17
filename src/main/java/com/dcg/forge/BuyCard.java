package com.dcg.forge;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.dcg.card.Card;
import com.dcg.command.CommandBase;
import com.dcg.command.CommandChain;
import com.dcg.game.AspectSystem;
import com.dcg.location.Deck;
import com.dcg.location.MoveLocation;
import com.dcg.ownership.Owned;
import com.dcg.player.AdjustPower;
import com.dcg.player.Turn;
import java.util.Optional;

public class BuyCard extends CommandBase {
  @Wire protected CommandChain commandChain;
  protected AspectSystem aspectSystem;
  protected ComponentMapper<Turn> mTurn;
  protected ComponentMapper<Card> mCard;
  protected ComponentMapper<Owned> mOwned;

  @Override
  protected boolean isInputValid() {
    Optional<Turn> turn =
        aspectSystem.getStream(Aspect.all(Turn.class)).mapToObj(mTurn::get).findFirst();
    if (!turn.isPresent()) {
      return false;
    }
    Card card = mCard.get(sourceEntity);
    if (turn.get().powerPool < card.cost) {
      System.out.printf("    Not enough power to buy *%s\n", card);
      return false;
    }
    return true;
  }

  @Override
  protected void run() {
    aspectSystem
        .getStream(Aspect.all(Turn.class))
        .forEach(
            playerEntity -> {
              mOwned.create(sourceEntity).owner = playerEntity;
              commandChain.addEnd(
                  new AdjustPower(-mCard.get(sourceEntity).cost).build(world, sourceEntity),
                  new MoveLocation(Deck.class).build(world, sourceEntity),
                  new DrawFromForge().build(world, -1));
            });
  }
}
