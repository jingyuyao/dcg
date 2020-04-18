package com.dcg.player;

import com.artemis.ComponentMapper;
import com.dcg.effect.AbstractEffectBuilder;
import java.util.List;
import java.util.Optional;

public class DeletePlayer extends AbstractEffectBuilder<Player> {
  protected ComponentMapper<Player> mPlayer;

  @Override
  protected void run(List<Integer> input) {
    getTargetEntities(input).forEach(world::delete);
  }

  @Override
  protected Optional<ComponentMapper<Player>> getComponentMapper() {
    return Optional.of(mPlayer);
  }
}
