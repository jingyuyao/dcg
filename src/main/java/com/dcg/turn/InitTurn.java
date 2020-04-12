package com.dcg.turn;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.ComponentMapper;
import com.dcg.command.Command;
import com.dcg.player.Player;

public class InitTurn extends Command {
  private final String playerName;
  protected AspectSubscriptionManager manager;
  protected ComponentMapper<Player> mPlayer;
  protected ComponentMapper<Turn> mTurn;

  public InitTurn(String playerName) {
    this.playerName = playerName;
  }

  @Override
  public void run() {
    for (int entity : manager.get(Aspect.all(Player.class)).getEntities().getData()) {
      if (mPlayer.get(entity).name.equals(playerName)) {
        mTurn.create(entity);
        return;
      }
    }
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), playerName);
  }
}
