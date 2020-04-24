package com.dcg.forge;

import com.dcg.card.Cards;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandArgs;
import com.dcg.game.CreateEntity;
import com.dcg.location.ForgeDeck;
import com.dcg.location.ThroneDeck;
import java.util.List;

public class InitializeForge extends AbstractCommandBuilder {
  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    for (CreateEntity createEntity : Cards.createForgeDeck()) {
      commandChain.addEnd(createEntity.tags(ForgeDeck.class).build(world, -1));
    }
    for (CreateEntity createEntity : Cards.createThroneDeck()) {
      commandChain.addEnd(createEntity.tags(ThroneDeck.class).build(world, -1));
    }
    commandChain.addEnd(new AddBuyCardToThroneDeck().build(world, -1));
    commandChain.addEnd(new RefillForgeRow().build(world, -1));
  }
}
