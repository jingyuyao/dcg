package com.dcg.game;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.command.CommandBase;
import com.dcg.player.Player;
import com.dcg.player.Turn;

public class InitTurn extends CommandBase {
  private final String playerName;
  protected OwnershipSystem ownershipSystem;
  protected ComponentMapper<Player> mPlayer;
  protected ComponentMapper<Turn> mTurn;

  public InitTurn(String playerName) {
    this.playerName = playerName;
  }

  @Override
  protected void run() {
    ownershipSystem
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
