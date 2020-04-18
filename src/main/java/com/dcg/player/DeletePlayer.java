package com.dcg.player;

import com.artemis.ComponentMapper;
import com.dcg.effect.AbstractEffectBuilder;
import java.util.List;

public class DeletePlayer extends AbstractEffectBuilder<Player> {
  protected ComponentMapper<Player> mPlayer;

  @Override
  protected void run(List<Integer> input) {
    getTargetEntities(input).forEach(world::delete);
  }

  @Override
  protected ComponentMapper<Player> getComponentMapper() {
    return mPlayer;
  }
}
