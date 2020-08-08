package com.dcg.forge;

import com.artemis.ComponentMapper;
import com.dcg.action.RemoveActions;
import com.dcg.card.Card;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandBuilder;
import com.dcg.command.CommandData;
import com.dcg.game.Owned;
import com.dcg.location.DiscardPile;
import com.dcg.location.MoveLocation;
import com.dcg.location.PlayArea;
import com.dcg.player.AdjustPower;

public class BuyCard extends AbstractCommandBuilder {
  private CommandBuilder chained;
  protected ComponentMapper<Card> mCard;
  protected ComponentMapper<Owned> mOwned;
  protected ComponentMapper<PlayArea> mPlayArea;

  public BuyCard() {
    addTriggerConditions(
        (originEntity, allowedTargets) -> !mPlayArea.has(originEntity),
        (cardEntity, allowedTargets) ->
            coreSystem.getTurn().powerPool >= mCard.get(cardEntity).cost);
  }

  public BuyCard chain(CommandBuilder chained) {
    this.chained = chained;
    return this;
  }

  @Override
  protected void run(CommandData data) {
    int originEntity = data.getOriginEntity();
    mOwned.create(originEntity).owner = coreSystem.getCurrentPlayerEntity();
    commandChain.addEnd(
        new RemoveActions().build(world, originEntity),
        AdjustPower.power(-mCard.get(originEntity).cost).build(world, originEntity),
        new MoveLocation(DiscardPile.class).build(world, originEntity));
    if (chained != null) {
      commandChain.addEnd(chained.build(world, originEntity));
    }
  }
}
