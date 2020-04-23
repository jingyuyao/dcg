package com.dcg.target;

import com.artemis.Aspect;
import com.dcg.card.Card;
import com.dcg.game.CoreSystem;
import com.dcg.location.DiscardPile;
import com.dcg.location.PlayArea;
import java.util.stream.Stream;

public class VoidbindableCards extends TargetSource {
  protected CoreSystem coreSystem;

  @Override
  protected Stream<Integer> getSource(int originEntity) {
    return coreSystem.getStream(Aspect.all(Card.class).one(PlayArea.class, DiscardPile.class));
  }
}
