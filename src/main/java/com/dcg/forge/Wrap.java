package com.dcg.forge;

import com.artemis.ComponentMapper;
import com.dcg.card.Card;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandArgs;
import com.dcg.command.CommandBuilder;
import com.dcg.game.Owned;
import com.dcg.location.MoveLocation;
import com.dcg.location.PlayArea;
import com.dcg.player.AdjustPower;
import java.util.List;

public class Wrap extends AbstractCommandBuilder {
  private CommandBuilder chained;
  protected ComponentMapper<Card> mCard;
  protected ComponentMapper<Owned> mOwned;
  protected ComponentMapper<PlayArea> mPlayArea;

  public Wrap() {
    addTriggerConditions(
        (originEntity, allowedTargets) -> !mPlayArea.has(originEntity),
        (cardEntity, allowedTargets) ->
            coreSystem.getTurn().powerPool >= mCard.get(cardEntity).cost,
        (originEntity, allowedTargets) -> coreSystem.getCurrentPlayer().wrapTokens > 0);
  }

  public Wrap chain(CommandBuilder chained) {
    this.chained = chained;
    return this;
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    coreSystem.getCurrentPlayer().wrapTokens -= 1;
    mOwned.create(originEntity).owner = coreSystem.getCurrentPlayerEntity();
    commandChain.addEnd(
        new AdjustPower(-mCard.get(originEntity).cost).build(world, originEntity),
        new MoveLocation(PlayArea.class).build(world, originEntity));
    if (chained != null) {
      commandChain.addEnd(chained.build(world, originEntity));
    }
  }
}
