package com.dcg.player;

import com.dcg.effect.AbstractEffectBuilder;
import com.dcg.targetsource.OriginEntityRoot;

abstract class PlayerEffect extends AbstractEffectBuilder {
  PlayerEffect() {
    setTargetSource(new OriginEntityRoot());
  }
}
