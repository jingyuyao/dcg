package com.dcg.forge;

import com.artemis.ComponentMapper;
import com.dcg.card.Card;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandArgs;
import com.dcg.game.Owned;
import com.dcg.location.DiscardPile;
import com.dcg.location.MoveLocation;
import com.dcg.player.AdjustPower;
import com.dcg.player.Turn;
import java.util.List;

public class BuyCard extends AbstractCommandBuilder {
  protected ComponentMapper<Turn> mTurn;
  protected ComponentMapper<Card> mCard;
  protected ComponentMapper<Owned> mOwned;

  public BuyCard() {
    addTriggerConditions(
        cardEntity ->
            coreSystem
                .getCurrentPlayerEntity()
                .map(mTurn::get)
                .allMatch(turn -> turn.powerPool >= mCard.get(cardEntity).cost));
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    coreSystem
        .getCurrentPlayerEntity()
        .forEach(
            playerEntity -> {
              mOwned.create(originEntity).owner = playerEntity;
              commandChain.addEnd(
                  new AdjustPower(-mCard.get(originEntity).cost).build(world, originEntity),
                  new MoveLocation(DiscardPile.class).build(world, originEntity),
                  new RefillForgeRow().build(world, -1));
            });
  }
}
