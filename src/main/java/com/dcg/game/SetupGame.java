package com.dcg.game;

import com.artemis.annotations.Wire;
import com.dcg.card.Cards;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandArgs;
import com.dcg.forge.InitializeForge;
import com.dcg.player.CreatePlayer;
import com.dcg.turn.InitTurn;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SetupGame extends AbstractCommandBuilder {
  private final List<String> players;
  @Wire Random random;

  public SetupGame(List<String> players) {
    this.players = players;
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    commandChain.addEnd(new InitializeForge().build(world, originEntity));
    List<CreateEntity> basicUnits = new ArrayList<>(Cards.createBasicUnits());
    for (String player : players) {
      int unitIndex = random.nextInt(basicUnits.size());
      CreateEntity unitEntity = basicUnits.get(unitIndex);
      basicUnits.remove(unitIndex);
      commandChain.addEnd(new CreatePlayer(player, unitEntity).build(world, originEntity));
    }
    commandChain.addEnd(new InitTurn(players.get(0)).build(world, originEntity));
  }
}
