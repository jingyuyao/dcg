package com.dcg.game;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.Target;
import com.dcg.player.Player;
import com.dcg.player.Turn;

public class InitTurn extends AbstractCommandBuilder {
  private final String playerName;
  protected ComponentMapper<Player> mPlayer;
  protected ComponentMapper<Turn> mTurn;

  public InitTurn(String playerName) {
    this.playerName = playerName;
  }

  @Override
  protected void run(Target target) {
    coreSystem
        .getStream(Aspect.all(Player.class))
        .filter(playerEntity -> mPlayer.get(playerEntity).name.equals(playerName))
        .findFirst()
        .ifPresent(playerEntity -> mTurn.create(playerEntity));
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), playerName);
  }
}
