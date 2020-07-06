package com.dcg.forge;

import com.artemis.ComponentMapper;
import com.dcg.card.Card;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandBuilder;
import com.dcg.command.CommandData;
import com.dcg.game.Owned;
import com.dcg.location.MoveLocation;
import com.dcg.location.PlayArea;
import com.dcg.player.AdjustPower;

public class Warp extends AbstractCommandBuilder {
  private CommandBuilder chained;
  protected ComponentMapper<Card> mCard;
  protected ComponentMapper<Owned> mOwned;
  protected ComponentMapper<PlayArea> mPlayArea;

  public Warp() {
    addTriggerConditions(
        (originEntity, allowedTargets) -> !mPlayArea.has(originEntity),
        (cardEntity, allowedTargets) ->
            coreSystem.getTurn().powerPool >= mCard.get(cardEntity).cost,
        (originEntity, allowedTargets) -> coreSystem.getCurrentPlayer().wrapTokens > 0);
  }

  public Warp chain(CommandBuilder chained) {
    this.chained = chained;
    return this;
  }

  @Override
  protected void run(CommandData data) {
    int originEntity = data.getOriginEntity();
    coreSystem.getCurrentPlayer().wrapTokens -= 1;
    mOwned.create(originEntity).owner = coreSystem.getCurrentPlayerEntity();
    commandChain.addEnd(
        AdjustPower.power(-mCard.get(originEntity).cost).build(world, originEntity),
        new MoveLocation(PlayArea.class).build(world, originEntity));
    if (chained != null) {
      commandChain.addEnd(chained.build(world, originEntity));
    }
  }
}
