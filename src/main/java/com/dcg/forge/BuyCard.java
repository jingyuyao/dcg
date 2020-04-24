package com.dcg.forge;

import com.artemis.ComponentMapper;
import com.dcg.card.Card;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandArgs;
import com.dcg.command.CommandBuilder;
import com.dcg.game.Owned;
import com.dcg.location.DiscardPile;
import com.dcg.location.MoveLocation;
import com.dcg.player.AdjustPower;
import com.dcg.turn.Turn;
import java.util.List;

public class BuyCard extends AbstractCommandBuilder {
  private CommandBuilder chained;
  protected ComponentMapper<Turn> mTurn;
  protected ComponentMapper<Card> mCard;
  protected ComponentMapper<Owned> mOwned;

  public BuyCard() {
    addTriggerConditions(
        (cardEntity, allowedTargets) ->
            coreSystem
                .getCurrentPlayerEntity()
                .map(mTurn::get)
                .allMatch(turn -> turn.powerPool >= mCard.get(cardEntity).cost));
  }

  public BuyCard chain(CommandBuilder chained) {
    this.chained = chained;
    return this;
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
                  new MoveLocation(DiscardPile.class).build(world, originEntity));
              if (chained != null) {
                commandChain.addEnd(chained.build(world, originEntity));
              }
            });
  }
}
