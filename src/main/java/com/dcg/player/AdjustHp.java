package com.dcg.player;

import com.artemis.ComponentMapper;
import com.dcg.command.CommandValue;
import java.util.List;

public class AdjustHp extends PlayerEffect {
  protected ComponentMapper<Player> mPlayer;

  public AdjustHp(int hp) {
    setCommandValue(() -> hp);
  }

  public AdjustHp(CommandValue commandValue) {
    setCommandValue(commandValue);
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, int value) {
    for (int playerEntity : targets) {
      Player player = mPlayer.get(playerEntity);
      player.hp += value;
      if (player.hp <= 0) {
        commandChain.addEnd(new DeletePlayer().build(world, playerEntity));
      }
    }
  }
}
