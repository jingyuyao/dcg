package com.dcg.game;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.command.Command;
import com.dcg.player.Player;
import com.dcg.turn.Turn;

public class InitTurn extends Command {
  private final String playerName;
  protected AspectSystem aspectSystem;
  protected ComponentMapper<Player> mPlayer;
  protected ComponentMapper<Turn> mTurn;

  public InitTurn(String playerName) {
    this.playerName = playerName;
  }

  @Override
  protected void run() {
    aspectSystem
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
