package com.dcg.debug;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.command.Command;
import com.dcg.player.Player;
import com.dcg.util.AspectSystem;

public class PrintPlayers extends Command {
  protected AspectSystem aspectSystem;
  protected ComponentMapper<Player> mPlayer;

  @Override
  public void run() {
    aspectSystem.getStream(Aspect.all(Player.class)).forEach(this::printPlayer);
  }

  private void printPlayer(int playerEntity) {
    System.out.printf("    *%d %s\n", playerEntity, mPlayer.get(playerEntity));
  }
}
