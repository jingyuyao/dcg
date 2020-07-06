package com.dcg.player;

import com.artemis.ComponentMapper;
import com.dcg.command.CommandData;
import java.util.List;

public class RefreshWarpTokens extends PlayerEffect {
  protected ComponentMapper<Player> mPlayer;

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandData args) {
    for (int playerEntity : targets) {
      Player player = mPlayer.get(playerEntity);
      player.wrapTokens = 2;
    }
  }
}
