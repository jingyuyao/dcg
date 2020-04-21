package com.dcg.player;

import com.artemis.ComponentMapper;
import com.dcg.effect.AbstractEffectBuilder;
import com.dcg.target.OriginEntityRoot;
import net.mostlyoriginal.api.utils.Preconditions;

abstract class PlayerEffect extends AbstractEffectBuilder {
  protected ComponentMapper<Player> mPlayer;

  PlayerEffect() {
    setTargetFunction(new OriginEntityRoot());
    addTargetConditions(
        target ->
            Preconditions.checkArgument(
                target.getTargets().stream().allMatch(mPlayer::has), "Target must be a player"));
  }
}
