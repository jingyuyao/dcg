package com.dcg.debug;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.game.OwnershipSystem;
import com.dcg.player.Player;
import com.dcg.player.Turn;

public class PrintPlayers extends DebugEntityCommand {
  protected OwnershipSystem ownershipSystem;
  protected ComponentMapper<Player> mPlayer;
  protected ComponentMapper<Turn> mTurn;

  @Override
  protected void run() {
    ownershipSystem.getStream(Aspect.all(Player.class)).forEach(this::printPlayer);
  }

  private void printPlayer(int playerEntity) {
    System.out.printf("    *%d %s\n", playerEntity, mPlayer.get(playerEntity));
    if (mTurn.has(playerEntity)) {
      System.out.printf("      %s\n", mTurn.get(playerEntity));
      printActions(playerEntity);
    }
  }
}
