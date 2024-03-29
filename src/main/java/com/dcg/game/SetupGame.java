package com.dcg.game;

import com.dcg.card.Cards;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandData;
import com.dcg.forge.InitializeForge;
import com.dcg.player.CreatePlayer;
import com.dcg.turn.InitTurn;
import java.util.List;

public class SetupGame extends AbstractCommandBuilder {
  private final List<String> players;

  public SetupGame(List<String> players) {
    this.players = players;
  }

  @Override
  protected void run(CommandData data) {
    int originEntity = data.getOriginEntity();
    commandChain.addEnd(new InitializeForge().build(world, originEntity));
    List<CreateEntity> units = Util.pickRandom(Cards.createBasicUnits(), players.size());
    for (int i = 0; i < players.size(); i++) {
      String player = players.get(i);
      CreateEntity createUnit = units.get(i % units.size());
      commandChain.addEnd(new CreatePlayer(player, createUnit).build(world, originEntity));
    }
    commandChain.addEnd(new InitTurn(players.get(0)).build(world, originEntity));
  }
}
