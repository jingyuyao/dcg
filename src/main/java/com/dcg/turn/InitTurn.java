package com.dcg.turn;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandArgs;
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
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), playerName);
  }
}
