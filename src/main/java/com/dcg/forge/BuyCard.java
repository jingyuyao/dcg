package com.dcg.forge;

import com.artemis.ComponentMapper;
import com.dcg.card.Card;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.game.Owned;
import com.dcg.location.DiscardPile;
import com.dcg.location.MoveLocation;
import com.dcg.player.AdjustPower;
import com.dcg.player.Turn;
import java.util.List;
import java.util.Optional;

public class BuyCard extends AbstractCommandBuilder {;
  protected ComponentMapper<Turn> mTurn;
  protected ComponentMapper<Card> mCard;
  protected ComponentMapper<Owned> mOwned;

  public BuyCard() {
    addWorldConditions(
        coreSystem -> {
          Optional<Turn> turn =
              coreSystem.getCurrentPlayerEntity().mapToObj(mTurn::get).findFirst();
          if (!turn.isPresent()) {
            return false;
          }
          Card card = mCard.get(sourceEntity);
          if (turn.get().powerPool < card.cost) {
            System.out.printf("    Not enough power to buy *%s\n", card);
            return false;
          }
          return true;
        });
  }

  @Override
  protected void run(List<Integer> input) {
    coreSystem
        .getCurrentPlayerEntity()
        .forEach(
            playerEntity -> {
              mOwned.create(sourceEntity).owner = playerEntity;
              commandChain.addEnd(
                  new AdjustPower(-mCard.get(sourceEntity).cost).build(world, sourceEntity),
                  new MoveLocation(DiscardPile.class).build(world, sourceEntity),
                  new RefillForgeRow().build(world, -1));
            });
  }
}
