package com.dcg.targetsource;

import com.artemis.Aspect;
import com.dcg.card.Card;
import com.dcg.game.CoreSystem;
import com.dcg.location.ForgeRow;
import java.util.stream.Stream;

public class ForgeRowCards extends TargetSource {
  protected CoreSystem coreSystem;

  @Override
  protected Stream<Integer> getSource(int originEntity) {
    return coreSystem.getStream(Aspect.all(Card.class, ForgeRow.class));
  }
}
