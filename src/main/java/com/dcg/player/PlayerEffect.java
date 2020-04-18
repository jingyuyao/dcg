package com.dcg.player;

import com.artemis.ComponentMapper;
import com.dcg.effect.AbstractEffectBuilder;
import com.dcg.targetsource.SourceEntityRoot;

abstract class PlayerEffect extends AbstractEffectBuilder {
  protected ComponentMapper<Player> mPlayer;

  PlayerEffect() {
    setTargetSource(new SourceEntityRoot());
    addTargetConditions(target -> target.get().stream().allMatch(mPlayer::has));
  }
}
