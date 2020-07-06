package com.dcg.forge;

import com.dcg.card.Cards;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandData;
import com.dcg.game.CreateEntity;
import com.dcg.location.ForgeDeck;
import com.dcg.location.MercenaryDeck;

public class InitializeForge extends AbstractCommandBuilder {
  @Override
  protected void run(CommandData data) {
    for (CreateEntity createEntity : Cards.createForgeDeck()) {
      commandChain.addEnd(createEntity.tags(ForgeDeck.class).build(world, -1));
    }
    for (CreateEntity createEntity : Cards.createForgeDeck()) {
      commandChain.addEnd(createEntity.tags(ForgeDeck.class).build(world, -1));
    }
    for (CreateEntity createEntity : Cards.createMercenaries()) {
      commandChain.addEnd(createEntity.tags(MercenaryDeck.class).build(world, -1));
    }
    commandChain.addEnd(new DrawForgeCards(6).build(world, -1));
  }
}
