package com.dcg.targetsource;

import com.artemis.Aspect;
import com.dcg.card.Card;
import com.dcg.game.CoreSystem;
import com.dcg.location.DiscardPile;
import com.dcg.location.Hand;
import java.util.stream.Stream;

public class VoidbindableCards extends TargetSource {
  protected CoreSystem coreSystem;

  @Override
  protected Stream<Integer> getSource(int originEntity) {
    int playerEntity = coreSystem.getRoot(originEntity);
    return coreSystem
        .getStream(Aspect.all(Card.class).one(Hand.class, DiscardPile.class))
        .filter(cardEntity -> coreSystem.getParent(cardEntity) == playerEntity);
  }
}
