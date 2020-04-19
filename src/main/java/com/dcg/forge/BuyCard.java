package com.dcg.forge;

import com.artemis.ComponentMapper;
import com.dcg.card.Card;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.Target;
import com.dcg.game.Owned;
import com.dcg.location.DiscardPile;
import com.dcg.location.MoveLocation;
import com.dcg.player.AdjustPower;
import com.dcg.player.Turn;

public class BuyCard extends AbstractCommandBuilder {
  protected ComponentMapper<Turn> mTurn;
  protected ComponentMapper<Card> mCard;
  protected ComponentMapper<Owned> mOwned;

  public BuyCard() {
    addWorldConditions(
        coreSystem ->
            coreSystem.getCurrentPlayerEntity().mapToObj(mTurn::get).findFirst().isPresent());
    addTargetConditions(
        target -> mCard.has(target.getFrom()),
        target ->
            coreSystem
                .getCurrentPlayerEntity()
                .mapToObj(mTurn::get)
                .allMatch(turn -> turn.powerPool >= mCard.get(target.getFrom()).cost));
  }

  @Override
  protected void run(Target target) {
    coreSystem
        .getCurrentPlayerEntity()
        .forEach(
            playerEntity -> {
              int cardEntity = target.getFrom();
              mOwned.create(cardEntity).owner = playerEntity;
              commandChain.addEnd(
                  new AdjustPower(-mCard.get(cardEntity).cost).build(world, cardEntity),
                  new MoveLocation(DiscardPile.class).build(world, cardEntity),
                  new RefillForgeRow().build(world, -1));
            });
  }
}
