package com.dcg.player;

import com.artemis.ComponentMapper;
import com.dcg.effect.AbstractEffectBuilder;
import com.dcg.source.SourceEntityRoot;

abstract class PlayerEffect extends AbstractEffectBuilder {
  protected ComponentMapper<Player> mPlayer;

  PlayerEffect() {
    setTargetFunction(new SourceEntityRoot());
    addTargetConditions(target -> target.getTo().stream().allMatch(mPlayer::has));
  }
}
