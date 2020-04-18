package com.dcg.card;

import com.artemis.ComponentMapper;
import com.dcg.effect.AbstractEffectBuilder;
import java.util.List;
import java.util.Optional;

public class DeleteCard extends AbstractEffectBuilder<Card> {
  protected ComponentMapper<Card> mCard;

  @Override
  protected void run(List<Integer> input) {
    getTargetEntities(input).forEach(world::delete);
  }

  @Override
  protected Optional<ComponentMapper<Card>> getComponentMapper() {
    return Optional.of(mCard);
  }
}
