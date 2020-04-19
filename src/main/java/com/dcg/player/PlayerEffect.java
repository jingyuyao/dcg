package com.dcg.player;

import com.artemis.ComponentMapper;
import com.dcg.effect.AbstractEffectBuilder;
import com.dcg.target.SourceEntityRoot;

abstract class PlayerEffect extends AbstractEffectBuilder {
  protected ComponentMapper<Player> mPlayer;

  PlayerEffect() {
    setTargetFunction(new SourceEntityRoot());
    addTargetConditions(
        target -> !target.getTargets().isEmpty(),
        target -> target.getTargets().stream().allMatch(mPlayer::has));
  }
}
