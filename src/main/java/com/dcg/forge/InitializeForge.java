package com.dcg.forge;

import com.dcg.card.Cards;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandArgs;
import com.dcg.game.CreateEntity;
import com.dcg.location.ForgeDeck;
import java.util.List;

public class InitializeForge extends AbstractCommandBuilder {
  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    for (CreateEntity createEntity : Cards.createForgeDeck()) {
      commandChain.addEnd(createEntity.tags(ForgeDeck.class).build(world, -1));
    }
    commandChain.addEnd(new DrawForgeDeck(6).build(world, -1));
  }
}
