package com.dcg.turn;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.command.Command;
import com.dcg.player.Player;
import com.dcg.util.AspectSystem;

public class InitTurn extends Command {
  private final String playerName;
  protected AspectSystem aspectSystem;
  protected ComponentMapper<Player> mPlayer;
  protected ComponentMapper<Turn> mTurn;

  public InitTurn(String playerName) {
    this.playerName = playerName;
  }

  @Override
  public void run() {
    for (int playerEntity : aspectSystem.get(Aspect.all(Player.class))) {
      if (mPlayer.get(playerEntity).name.equals(playerName)) {
        mTurn.create(playerEntity);
        return;
      }
    }
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), playerName);
  }
}
