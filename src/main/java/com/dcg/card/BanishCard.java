package com.dcg.card;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandBuilder;
import com.dcg.command.CommandData;
import com.dcg.forge.DrawForgeCards;
import com.dcg.location.BanishedPile;
import com.dcg.location.ForgeRow;
import com.dcg.location.MoveLocation;

/**
 * Cards are permanent entities in the game. They are moved to the {@code BanishedPile} instead of
 * being deleted.
 */
public class BanishCard extends AbstractCommandBuilder {
  private CommandBuilder chained;
  protected ComponentMapper<ForgeRow> mForgeRow;

  public BanishCard chain(CommandBuilder builder) {
    this.chained = builder;
    return this;
  }

  @Override
  protected void run(CommandData data) {
    for (int cardEntity : data.getTargets()) {
      if (mForgeRow.has(cardEntity)) {
        commandChain.addEnd(new DrawForgeCards(1).build(world, -1));
      }
      commandChain.addEnd(new MoveLocation(BanishedPile.class).build(world, cardEntity));
    }
    if (chained != null) {
      commandChain.addEnd(chained.build(world, data.getOriginEntity()));
    }
  }

  @Override
  protected String getDescription(CommandData data) {
    return String.format(
        "banishing %s", coreSystem.toNames(data.getOriginEntity(), data.getTargets()));
  }

  @Override
  protected boolean isClientVisible(CommandData data) {
    return true;
  }
}
