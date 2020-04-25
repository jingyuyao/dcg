package com.dcg.turn;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.card.Cards;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandArgs;
import com.dcg.forge.InitializeBuyCard;
import com.dcg.game.CreateEntity;
import com.dcg.location.ThroneDeck;
import com.dcg.player.Player;
import java.util.List;

public class InitTurn extends AbstractCommandBuilder {
  private final String playerName;
  protected ComponentMapper<Turn> mTurn;

  public InitTurn(String playerName) {
    this.playerName = playerName;
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    coreSystem
        .findByName(playerName, Aspect.all(Player.class))
        .findFirst()
        .ifPresent(playerEntity -> mTurn.create(playerEntity));
    int playerCount = (int) coreSystem.getStream(Aspect.all(Player.class)).count();
    for (CreateEntity createEntity : Cards.createThroneDeck(playerCount)) {
      commandChain.addEnd(createEntity.tags(ThroneDeck.class).build(world, -1));
    }
    commandChain.addEnd(new InitializeBuyCard().build(world, -1));
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), playerName);
  }
}
