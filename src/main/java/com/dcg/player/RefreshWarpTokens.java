package com.dcg.player;

import com.artemis.ComponentMapper;
import com.dcg.command.CommandData;

public class RefreshWarpTokens extends PlayerEffect {
  protected ComponentMapper<Player> mPlayer;

  @Override
  protected void run(CommandData data) {
    for (int playerEntity : data.getTargets()) {
      Player player = mPlayer.get(playerEntity);
      player.wrapTokens = 2;
    }
  }

  @Override
  protected String getDescription(CommandData data) {
    return "refreshed warp tokens";
  }

  @Override
  protected boolean isClientVisible(CommandData data) {
    return true;
  }
}
