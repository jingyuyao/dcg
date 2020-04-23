package com.dcg.player;

import com.dcg.command.OriginRootSelector;
import com.dcg.effect.AbstractEffectBuilder;
import com.dcg.target.AllPlayers;

abstract class PlayerEffect extends AbstractEffectBuilder {
  PlayerEffect() {
    setTargetSource(new AllPlayers());
    setTargetSelector(new OriginRootSelector());
  }
}
