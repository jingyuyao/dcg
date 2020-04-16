package com.dcg.debug;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.game.AspectSystem;
import com.dcg.player.Player;
import com.dcg.turn.TurnSystem;

public class PrintPlayers extends DebugEntityCommand {
  protected AspectSystem aspectSystem;
  protected ComponentMapper<Player> mPlayer;
  protected TurnSystem turnSystem;

  @Override
  public void run() {
    aspectSystem.getStream(Aspect.all(Player.class)).forEach(this::printPlayer);
  }

  private void printPlayer(int playerEntity) {
    System.out.printf("    *%d %s\n", playerEntity, mPlayer.get(playerEntity));
    if (playerEntity == turnSystem.getPlayerEntity()) {
      System.out.printf("      %s\n", turnSystem.getTurn());
      printActions(playerEntity);
    }
  }
}
