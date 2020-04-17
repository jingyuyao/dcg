package com.dcg.player;

import com.artemis.ComponentMapper;
import com.dcg.effect.AbstractEffectBuilder;

public class DeletePlayer extends AbstractEffectBuilder<Player> {
  protected ComponentMapper<Player> mPlayer;

  @Override
  protected void run() {
    getTargetEntities().forEach(world::delete);
  }

  @Override
  protected ComponentMapper<Player> getComponentMapper() {
    return mPlayer;
  }
}
