package com.dcg.player;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.targetsource.SourceEntityRoot;

abstract class PlayerEffect extends AbstractCommandBuilder {
  protected ComponentMapper<Player> mPlayer;

  PlayerEffect() {
    setTargetSource(new SourceEntityRoot());
    addTargetConditions(input -> input.stream().allMatch(mPlayer::has));
  }
}
