package com.dcg.player;

import com.artemis.ComponentMapper;
import com.dcg.command.CommandArgs;
import java.util.List;

public class RefreshFlashTokens extends PlayerEffect {
  protected ComponentMapper<Player> mPlayer;

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    for (int playerEntity : targets) {
      Player player = mPlayer.get(playerEntity);
      player.flashTokens = 2;
    }
  }
}
